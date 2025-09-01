/**
 * Implementación del Data Access Object (DAO) para interactuar con la API de MTCenter.
 *
 * Esta clase proporciona los métodos concretos para autenticación, recargas, consultas
 * y gestión de productos a través del servicio MTCenter utilizando Spring RestTemplate.
 * Maneja automáticamente la renovación de tokens de autenticación.
 */
package com.lksbaas.mx.dao.impl;

import com.lksbaas.mx.dao.IMTCenterDAO;
import com.lksbaas.mx.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class ImplMTCenterDAO implements IMTCenterDAO {

    /**
     * Logger para registro de eventos y errores
     */
    private static final Logger logger = LoggerFactory.getLogger(ImplMTCenterDAO.class);

    // ============ CONFIGURACIÓN DESDE APPLICATION.PROPERTIES ============
    @Value("${mtcenter.api.base-url:http://189.204.158.163/clientes/api/}")
    private String baseUrl;

    @Value("${mtcenter.credentials.cadena}")
    private Integer cadena;

    @Value("${mtcenter.credentials.establecimiento}")
    private Integer establecimiento;

    @Value("${mtcenter.credentials.terminal}")
    private Integer terminal;

    @Value("${mtcenter.credentials.cajero}")
    private Integer cajero;

    @Value("${mtcenter.credentials.clave}")
    private String clave;

    // ============ COMPONENTES Y ESTADO ============

    /**
     * RestTemplate para realizar peticiones HTTP a la API de MTCenter
     */
    private final RestTemplate restTemplate;

    /**
     * Token de autenticación actual para las peticiones
     */
    private String currentToken;

    /**
     * Fecha y hora de expiración del token actual
     */
    private LocalDateTime tokenExpiry;

    // ============ CONSTRUCTOR ============

    public ImplMTCenterDAO() {
        this.restTemplate = new RestTemplate();
    }

    // ============ MÉTODOS DE LA INTERFAZ IMTCenterDAO ============

    /**
     * Autentica con el servicio MTCenter y obtiene un token de acceso
     *
     * @return MTCenterAuthResponse con la respuesta de autenticación
     * @throws RuntimeException si ocurre un error en la autenticación
     */
    @Override
    public MTCenterAuthResponse authenticate() {
        try {
            // Crear request de autenticación con credenciales desde properties
            MTCenterAuthRequest request = new MTCenterAuthRequest(
                    cadena, establecimiento, terminal, cajero, clave
            );

            // Configurar headers para petición JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<MTCenterAuthRequest> entity = new HttpEntity<>(request, headers);

            String url = baseUrl + "token/authenticate";
            logger.info("Autenticando con MTCenter: {}", url);

            // Realizar petición POST de autenticación
            ResponseEntity<MTCenterAuthResponse> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, MTCenterAuthResponse.class
            );

            MTCenterAuthResponse authResponse = response.getBody();
            if (authResponse != null && authResponse.getCodigoRespuesta() == 0) {
                // Almacenar token y calcular expiración con buffer de seguridad
                currentToken = authResponse.getToken();
                // Token válido por 24 horas menos 5 minutos de buffer
                tokenExpiry = LocalDateTime.now().plusSeconds(
                        Integer.parseInt(authResponse.getExpiresIn()) - 300
                );
                logger.info("Autenticación exitosa");
            }

            return authResponse;

        } catch (Exception e) {
            logger.error("Error en autenticación MTCenter", e);
            throw new RuntimeException("Error de autenticación con MTCenter", e);
        }
    }

    /**
     * Realiza una recarga internacional a través del servicio MTCenter
     *
     * @param recarga Objeto RecargaInternacional con los datos de la recarga
     * @return RecargaInternacional con la respuesta de la recarga
     * @throws RuntimeException si ocurre un error en la recarga
     */
    @Override
    public RecargaInternacional realizarRecarga(RecargaInternacional recarga) {
        try {
            String token = getValidToken(); // Obtener token válido

            // Configurar headers con token de autenticación
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);
            HttpEntity<RecargaInternacional> entity = new HttpEntity<>(recarga, headers);

            String url = baseUrl + "itae/recarga";
            logger.info("Realizando recarga internacional: {} - {}",
                    recarga.getTelefono(), recarga.getImporte());

            // Realizar petición POST de recarga
            ResponseEntity<RecargaInternacional> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, RecargaInternacional.class
            );

            return response.getBody();

        } catch (Exception e) {
            logger.error("Error realizando recarga", e);
            throw new RuntimeException("Error en recarga internacional", e);
        }
    }

    /**
     * Consulta el saldo disponible en la cuenta MTCenter
     *
     * @return SaldoMTCenter con la información del saldo
     * @throws RuntimeException si ocurre un error en la consulta
     */
    @Override
    public SaldoMTCenter consultarSaldo() {
        try {
            String token = getValidToken(); // Obtener token válido

            // Crear request con fecha/hora actual
            SaldoRequest request = new SaldoRequest();
            request.setFechaHora(LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            ));

            // Configurar headers con token de autenticación
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);
            HttpEntity<SaldoRequest> entity = new HttpEntity<>(request, headers);

            String url = baseUrl + "itae/saldo";

            // Realizar petición POST de consulta de saldo
            ResponseEntity<SaldoMTCenter> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, SaldoMTCenter.class
            );

            return response.getBody();

        } catch (Exception e) {
            logger.error("Error consultando saldo", e);
            throw new RuntimeException("Error consultando saldo", e);
        }
    }

    /**
     * Consulta el catálogo de productos disponibles en MTCenter
     *
     * @return ProductosMTCenter con la lista de productos
     * @throws RuntimeException si ocurre un error en la consulta
     */
    @Override
    public ProductosMTCenter consultarProductos() {
        try {
            String token = getValidToken(); // Obtener token válido

            // Configurar headers con token de autenticación
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = baseUrl + "itae/productos";

            // Realizar petición GET de productos (no necesita body)
            ResponseEntity<ProductosMTCenter> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, ProductosMTCenter.class
            );

            return response.getBody();

        } catch (Exception e) {
            logger.error("Error consultando productos", e);
            throw new RuntimeException("Error consultando productos", e);
        }
    }

    /**
     * Consulta el estado de una recarga previamente realizada
     *
     * @param consulta Objeto ConsultaRecarga con los datos de consulta
     * @return RecargaInternacional con la información de la recarga consultada
     * @throws RuntimeException si ocurre un error en la consulta
     */
    @Override
    public RecargaInternacional consultarRecarga(ConsultaRecarga consulta) {
        try {
            String token = getValidToken(); // Obtener token válido

            // Configurar headers con token de autenticación
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);
            HttpEntity<ConsultaRecarga> entity = new HttpEntity<>(consulta, headers);

            String url = baseUrl + "itae/consulta";

            // Realizar petición POST de consulta de recarga
            ResponseEntity<RecargaInternacional> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, RecargaInternacional.class
            );

            return response.getBody();

        } catch (Exception e) {
            logger.error("Error consultando recarga", e);
            throw new RuntimeException("Error consultando recarga", e);
        }
    }

    // ============ MÉTODO PRIVADO PARA GESTIÓN DE TOKENS ============

    /**
     * Obtiene un token válido para autenticación.
     * Renueva el token automáticamente si está expirado o no existe.
     *
     * @return Token de autenticación válido
     */
    private String getValidToken() {
        if (currentToken == null || tokenExpiry == null || LocalDateTime.now().isAfter(tokenExpiry)) {
            authenticate(); // Renovar token si es necesario
        }
        return currentToken;
    }
}
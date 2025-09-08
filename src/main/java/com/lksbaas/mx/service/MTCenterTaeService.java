package com.lksbaas.mx.service;


import com.lksbaas.mx.dto.auth.AuthRequest;
import com.lksbaas.mx.dto.auth.AuthResponse;
import com.lksbaas.mx.dto.tae.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class MTCenterTaeService {

    private final WebClient webClient;
    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> tokenExpiry = new ConcurrentHashMap<>();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final AuthService authService;

    @Autowired
    public MTCenterTaeService(WebClient mtCenterWebClient, AuthService authService) {
        this.webClient = mtCenterWebClient;
        this.authService = authService;
    }

    /**
     * Obtiene un token válido (reutiliza si está vigente o genera uno nuevo)
     */
    public String getValidToken(AuthRequest authRequest) {
        String cacheKey = generateCacheKey(authRequest);

        // Verificar si el token actual es válido
        if (tokenCache.containsKey(cacheKey) && tokenExpiry.containsKey(cacheKey)) {
            LocalDateTime expiry = tokenExpiry.get(cacheKey);
            // Si el token expira en más de 5 minutos, lo usamos
            if (expiry.isAfter(LocalDateTime.now().plusMinutes(5))) {
                return tokenCache.get(cacheKey);
            }
        }

        // Generar nuevo token
        AuthResponse authResponse = authService.authenticate(authRequest);
        if (authResponse.getCodigoRespuesta() == 0) {
            String token = authResponse.getToken();
            tokenCache.put(cacheKey, token);
            tokenExpiry.put(cacheKey, LocalDateTime.now().plusHours(24));
            return token;
        } else {
            throw new RuntimeException("Error en autenticación: " + authResponse.getMensajeRespuesta());
        }
    }

    /**
     * Realiza una recarga TAE
     */
    public RecargaResponse realizarRecarga(RecargaRequest recargaRequest, String token) {
        try {
            return webClient.post()
                    .uri("/tae/recarga")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(recargaRequest)
                    .retrieve()
                    .bodyToMono(RecargaResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al realizar recarga: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error de conectividad en recarga", e);
        }
    }

    /**
     * Consulta el saldo disponible
     */
    public SaldoResponse consultarSaldo(String token) {
        try {
            SaldoRequest saldoRequest = new SaldoRequest(getCurrentDateTime());

            return webClient.post()
                    .uri("/tae/saldo")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(saldoRequest)
                    .retrieve()
                    .bodyToMono(SaldoResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al consultar saldo: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error de conectividad en consulta de saldo", e);
        }
    }

    /**
     * Consulta el estado de una recarga
     */
    public ConsultaRecargaResponse consultarRecarga(String token, ConsultaRecargaRequest consultaRequest) {
        try {
            return webClient.post()
                    .uri("/tae/consulta")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(consultaRequest)
                    .retrieve()
                    .bodyToMono(ConsultaRecargaResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al consultar recarga: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error de conectividad en consulta de recarga", e);
        }
    }

    /**
     * Obtiene la lista de productos disponibles
     */
    public ProductosResponse obtenerProductos(String token) {
        try {
            return webClient.get()
                    .uri("/tae/productos")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(ProductosResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al obtener productos: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error de conectividad en consulta de productos", e);
        }
    }

    /**
     * Realiza consultas consecutivas según la documentación
     * Implementa el patrón de consulta recomendado por MTCenter
     */
    public ConsultaRecargaResponse consultarRecargaConReintentos(String token,
                                                                 ConsultaRecargaRequest consultaRequest) {
        int maxIntentos = 8;
        int tiempoEspera = 5; // segundos

        for (int intento = 1; intento <= maxIntentos; intento++) {
            try {
                // Esperar antes de cada consulta (excepto la primera que ya esperó 30s)
                if (intento > 1) {
                    Thread.sleep(tiempoEspera * 1000);
                }

                ConsultaRecargaResponse response = consultarRecarga(token, consultaRequest);

                // Si el código es 0 (exitosa), retornar inmediatamente
                if (response.getCodigoRespuesta() == 0) {
                    return response;
                }

                // Si es -600 o 71, continuar consultando
                if (response.getCodigoRespuesta() == -600 || response.getCodigoRespuesta() == 71) {
                    if (intento == maxIntentos) {
                        // Si es la última consulta, retornar el resultado
                        return response;
                    }
                    continue;
                }

                // Para cualquier otro código, retornar inmediatamente
                return response;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Consulta interrumpida", e);
            } catch (Exception e) {
                if (intento == maxIntentos) {
                    throw e;
                }
            }
        }

        throw new RuntimeException("Se agotaron los intentos de consulta");
    }

    /**
     * Genera una clave única para el cache de tokens
     */
    private String generateCacheKey(AuthRequest authRequest) {
        return String.format("%d-%d-%d-%d",
                authRequest.getCadena(),
                authRequest.getEstablecimiento(),
                authRequest.getTerminal(),
                authRequest.getCajero());
    }

    /**
     * Obtiene la fecha y hora actual en el formato requerido por MTCenter
     */
    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DATE_FORMAT);
    }
}

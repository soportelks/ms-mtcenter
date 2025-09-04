package com.lksbaas.mx.service.pines;

import com.lksbaas.mx.dto.pines.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MTCenterPinesService {

    @Value("${mtcenter.api.base-url}")
    private String baseUrl;

    private final WebClient webClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public MTCenterPinesService() {
        this.webClient = WebClient.builder().build();
    }

    public AuthResponse authenticate(AuthRequest credencial) {
        try {
            return webClient.post()
                    .uri(baseUrl + "token/authenticate")
                    .header("Content-Type", "application/json")
                    .body(BodyInserters.fromValue(credencial))
                    .retrieve()
                    .bodyToMono(AuthResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar con MTCenter: " + e.getMessage(), e);
        }
    }

    public RecargaResponse recargarPin(RecargaRequest recarga, String token) {
        try {
            return webClient.post()
                    .uri(baseUrl + "pin/pin")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(BodyInserters.fromValue(recarga))
                    .retrieve()
                    .bodyToMono(RecargaResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar recarga de pin: " + e.getMessage(), e);
        }
    }

    public ConsultaRecargaResponse consultarRecarga(ConsultaRecargaRequest consulta, String token) {
        try {
            return webClient.post()
                    .uri(baseUrl + "pin/consulta")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(BodyInserters.fromValue(consulta))
                    .retrieve()
                    .bodyToMono(ConsultaRecargaResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar recarga: " + e.getMessage(), e);
        }
    }

    public SaldoResponse consultarSaldo(SaldoRequest consulta, String token) {
        try {
            return webClient.post()
                    .uri(baseUrl + "pin/saldo")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(BodyInserters.fromValue(consulta))
                    .retrieve()
                    .bodyToMono(SaldoResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar saldo: " + e.getMessage(), e);
        }
    }

    public ProductosResponse consultarProductos(String token) {
        try {
            return webClient.get()
                    .uri(baseUrl + "pin/productos")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(ProductosResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar productos: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para generar fecha/hora actual
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(formatter);
    }

    // Método auxiliar para validar y manejar consultas iterativas según el manual
    public ConsultaRecargaResponse consultarRecargaConReintentos(ConsultaRecargaRequest consulta, String token, int maxIntentos) {
        ConsultaRecargaResponse response = null;
        int intentos = 0;

        while (intentos < maxIntentos) {
            try {
                response = consultarRecarga(consulta, token);

                // Según el manual: códigos -600 y 71 requieren seguir consultando
                if (response.getCodigoRespuesta() != null) {
                    Integer codigo = response.getCodigoRespuesta();

                    // Si es 0 (exitosa), terminar
                    if (codigo == 0) {
                        break;
                    }

                    // Si es -600 o 71, continuar consultando
                    if (codigo != -600 && codigo != 71) {
                        // Cualquier otro código, terminar (transacción no exitosa)
                        break;
                    }
                }

                intentos++;

                // Esperar 5 segundos antes del siguiente intento (según manual)
                if (intentos < maxIntentos) {
                    Thread.sleep(5000);
                }

            } catch (Exception e) {
                throw new RuntimeException("Error en consulta de recarga con reintentos: " + e.getMessage(), e);
            }
        }

        return response;
    }

    // Método de conveniencia para consultar saldo actual
    public SaldoResponse consultarSaldoActual(String token) {
        SaldoRequest request = new SaldoRequest(getCurrentDateTime());
        return consultarSaldo(request, token);
    }

    // Método de conveniencia para crear recarga con fecha actual
    public RecargaRequest crearRecargaRequest(String sku, Integer noTransaccion, String referencia1, String referencia2) {
        return new RecargaRequest(sku, noTransaccion, getCurrentDateTime(), referencia1, referencia2);
    }

    // Método de conveniencia para crear consulta de recarga con fecha actual
    public ConsultaRecargaRequest crearConsultaRecargaRequest(String referencia1, String referencia2, String sku,
                                                              Integer noTransaccionPin, String fechaHoraPin, Integer noTransaccion) {
        return new ConsultaRecargaRequest(referencia1, referencia2, sku, noTransaccionPin, fechaHoraPin, noTransaccion, getCurrentDateTime());
    }
}

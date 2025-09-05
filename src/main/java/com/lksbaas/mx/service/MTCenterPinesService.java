package com.lksbaas.mx.service;

import com.lksbaas.mx.dto.pines.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MTCenterPinesService {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(MTCenterPinesService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public MTCenterPinesService(WebClient webClient) {
        this.webClient = webClient;
    }

    public AuthResponse authenticate(AuthRequest credencial) {
        try {
            return webClient.post()
                    .uri("/token/authenticate")
                    .bodyValue(credencial)
                    .retrieve()
                    .bodyToMono(AuthResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en autenticación: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en autenticación: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en autenticación", e);
            throw new RuntimeException("Error al autenticar con MTCenter");
        }
    }

    public RecargaResponse recargarPin(RecargaRequest recarga, String token) {
        try {
            return webClient.post()
                    .uri("/pin/pin")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(recarga)
                    .retrieve()
                    .bodyToMono(RecargaResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(2)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en recarga de PIN: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en recarga de PIN: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en recarga de PIN", e);
            throw new RuntimeException("Error al realizar recarga de PIN");
        }
    }

    public ConsultaRecargaResponse consultarRecarga(ConsultaRecargaRequest consulta, String token) {
        try {
            return webClient.post()
                    .uri("/pin/consulta")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(consulta)
                    .retrieve()
                    .bodyToMono(ConsultaRecargaResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta de recarga: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de recarga: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta de recarga", e);
            throw new RuntimeException("Error al consultar recarga");
        }
    }

    public SaldoResponse consultarSaldo(SaldoRequest consulta, String token) {
        try {
            return webClient.post()
                    .uri("/pin/saldo")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(consulta)
                    .retrieve()
                    .bodyToMono(SaldoResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta de saldo: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de saldo: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta de saldo", e);
            throw new RuntimeException("Error al consultar saldo");
        }
    }

    public ProductosResponse consultarProductos(String token) {
        try {
            return webClient.get()
                    .uri("/pin/productos")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(ProductosResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta de productos: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de productos: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta de productos", e);
            throw new RuntimeException("Error al consultar productos");
        }
    }

    // Método auxiliar para generar fecha/hora actual
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DATE_FORMAT);
    }

    // Método para realizar consultas con retry según el manual
    public ConsultaRecargaResponse consultarRecargaConReintentos(ConsultaRecargaRequest consulta, String token, int maxIntentos) {
        int intentos = 0;

        while (intentos < maxIntentos) {
            try {
                if (intentos > 0) {
                    Thread.sleep(5000);
                }

                ConsultaRecargaResponse response = consultarRecarga(consulta, token);

                // Según el manual: códigos -600 y 71 requieren seguir consultando
                if (response.getCodigoRespuesta() != null) {
                    Integer codigo = response.getCodigoRespuesta();

                    // Si es 0 (exitosa), terminar
                    if (codigo == 0) {
                        return response;
                    }

                    // Si es -600 o 71, continuar consultando
                    if (codigo != -600 && codigo != 71) {
                        // Cualquier otro código, terminar (transacción no exitosa)
                        return response;
                    }
                }

                intentos++;
                log.info("Consulta {}/{} - Código: {}", intentos, maxIntentos, response.getCodigoRespuesta());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Consulta interrumpida");
            } catch (Exception e) {
                log.error("Error en consulta {}/{}", intentos + 1, maxIntentos, e);
                if (intentos == maxIntentos - 1) {
                    throw new RuntimeException("Se agotaron los intentos de consulta", e);
                }
            }
        }

        throw new RuntimeException("Se agotaron los intentos de consulta");
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
package com.lksbaas.mx.service;

import com.lksbaas.mx.dto.auth.AuthRequest;
import com.lksbaas.mx.dto.auth.AuthResponse;
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

    private final AuthService authService;

    public MTCenterPinesService(WebClient webClient, AuthService authService) {
        this.webClient = webClient;
        this.authService = authService;
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
    // En MTCenterPinesService
    public ConsultaRecargaResponse consultarRecargaConReintentos(ConsultaRecargaRequest consulta, String token, int maxIntentos) {
        int intervalo = 5; // segundos entre consultas
        int tiempoTotal = 60; // segundos totales

        for (int intento = 1; intento <= maxIntentos; intento++) {
            try {
                // Esperar antes de la primera consulta (30-40 segundos según manual)
                if (intento == 1) {
                    Thread.sleep(30000); // 30 segundos
                } else {
                    Thread.sleep(intervalo * 1000);
                }

                ConsultaRecargaResponse response = consultarRecarga(consulta, token);
                Integer codigo = response.getCodigoRespuesta();

                // Según manual Tablas 6-9
                if (codigo == 0) {
                    return response; // Transacción exitosa
                } else if (codigo != -600 && codigo != 71) {
                    return response; // Error definitivo
                }

                // Si es -600 o 71, continuar consultando
                log.info("Consulta {}/{} - Código: {}", intento, maxIntentos, codigo);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Consulta interrumpida");
            } catch (Exception e) {
                log.error("Error en consulta {}/{}", intento, maxIntentos, e);
            }
        }

        // Si llegamos aquí, todas las consultas devolvieron -600 o 71
        throw new RuntimeException("Transacción no exitosa después de " + maxIntentos + " intentos");
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
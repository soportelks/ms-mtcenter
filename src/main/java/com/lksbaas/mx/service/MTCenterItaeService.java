package com.lksbaas.mx.service;

import com.lksbaas.mx.dto.auth.AuthRequest;
import com.lksbaas.mx.dto.auth.AuthResponse;
import com.lksbaas.mx.dto.itae.*;
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
public class MTCenterItaeService {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(MTCenterItaeService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final AuthService authService;

    public MTCenterItaeService(WebClient webClient, AuthService authService) {
        this.webClient = webClient;
        this.authService = authService;
    }

    public RecargaResponse realizarRecarga(RecargaRequest request, String token) {
        try {
            return webClient.post()
                    .uri("/itae/recarga")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(RecargaResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(2)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en recarga: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en recarga: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en recarga", e);
            throw new RuntimeException("Error al realizar recarga");
        }
    }

    public SaldoResponse consultarSaldo(String token) {
        try {
            SaldoRequest request = new SaldoRequest(LocalDateTime.now().format(DATE_FORMAT));

            return webClient.post()
                    .uri("/itae/saldo")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(SaldoResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta saldo: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de saldo: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta saldo", e);
            throw new RuntimeException("Error al consultar saldo");
        }
    }

    public ConsultaRecargaResponse consultarRecarga(ConsultaRecargaRequest request, String token) {
        try {
            return webClient.post()
                    .uri("/itae/consulta")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ConsultaRecargaResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta recarga: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de recarga: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta recarga", e);
            throw new RuntimeException("Error al consultar recarga");
        }
    }

    public ProductosResponse consultarProductos(String token) {
        try {
            return webClient.get()
                    .uri("/itae/productos")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(ProductosResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta productos: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de productos: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta productos", e);
            throw new RuntimeException("Error al consultar productos");
        }
    }

    // Método para realizar consultas con retry según el manual
    public ConsultaRecargaResponse consultarRecargaConRetry(ConsultaRecargaRequest request, String token) {
        int maxConsultas = 8;
        int intervalo = 5; // segundos

        for (int i = 0; i < maxConsultas; i++) {
            try {
                if (i > 0) {
                    Thread.sleep(intervalo * 1000);
                }

                ConsultaRecargaResponse response = consultarRecarga(request, token);

                // Si es exitosa (0) o error diferente a -600 y 71, terminar
                if (response.getCodigoRespuesta() != null &&
                        response.getCodigoRespuesta() != -600 &&
                        response.getCodigoRespuesta() != 71) {
                    return response;
                }

                log.info("Consulta {}/{} - Código: {}", i + 1, maxConsultas, response.getCodigoRespuesta());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Consulta interrumpida");
            } catch (Exception e) {
                log.error("Error en consulta {}/{}", i + 1, maxConsultas, e);
                if (i == maxConsultas - 1) {
                    throw e;
                }
            }
        }

        throw new RuntimeException("Se agotaron los intentos de consulta");
    }
}

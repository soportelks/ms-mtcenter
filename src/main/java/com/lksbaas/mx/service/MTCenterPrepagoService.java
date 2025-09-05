package com.lksbaas.mx.service;

import com.lksbaas.mx.dto.prepago.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class MTCenterPrepagoService {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(MTCenterPrepagoService.class);

    public MTCenterPrepagoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            return webClient.post()
                    .uri("/token/authenticate")
                    .bodyValue(authRequest)
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

    public RecargaPrepagoResponse realizarRecargaPrepago(RecargaPrepagoRequest recargaRequest, String token) {
        try {
            return webClient.post()
                    .uri("/prepago/prepago")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(recargaRequest)
                    .retrieve()
                    .bodyToMono(RecargaPrepagoResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(2)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en recarga prepago: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en recarga prepago: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en recarga prepago", e);
            throw new RuntimeException("Error al realizar recarga prepago");
        }
    }

    public ConsultaSaldoResponse consultarSaldo(ConsultaSaldoRequest saldoRequest, String token) {
        try {
            return webClient.post()
                    .uri("/prepago/saldo")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(saldoRequest)
                    .retrieve()
                    .bodyToMono(ConsultaSaldoResponse.class)
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

    public ConsultaPrepagoResponse consultarPrepago(ConsultaPrepagoRequest consultaRequest, String token) {
        try {
            return webClient.post()
                    .uri("/prepago/consulta")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(consultaRequest)
                    .retrieve()
                    .bodyToMono(ConsultaPrepagoResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta de prepago: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de prepago: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta de prepago", e);
            throw new RuntimeException("Error al consultar prepago");
        }
    }

    public ProductosResponse consultarProductos(String token) {
        try {
            return webClient.get()
                    .uri("/prepago/productos")
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
}
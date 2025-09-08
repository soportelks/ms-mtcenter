package com.lksbaas.mx.service;

import com.lksbaas.mx.dto.auth.AuthRequest;
import com.lksbaas.mx.dto.auth.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class AuthService {
    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(WebClient webClient) {
        this.webClient = webClient;
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            return webClient.post()
                    .uri("/token/authenticate")
                    .bodyValue(request)
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
}
package com.lksbaas.mx.service;

import com.lksbaas.mx.dto.servicios.*;
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
public class MTCenterServiciosService {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(MTCenterServiciosService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public MTCenterServiciosService(WebClient webClient) {
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

    public PagoServicioResponse pagarServicio(PagoServicioRequest pagoRequest, String token) {
        try {
            return webClient.post()
                    .uri("/emisores/servicio") // Ruta corregida según manual
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(pagoRequest)
                    .retrieve()
                    .bodyToMono(PagoServicioResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(2)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en pago de servicio: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en pago de servicio: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en pago de servicio", e);
            throw new RuntimeException("Error al realizar pago de servicio");
        }
    }

    public ConsultaRecargaResponse consultarServicio(ConsultaRecargaRequest consultaRequest, String token) {
        try {
            return webClient.post()
                    .uri("/emisores/consulta") // Ruta corregida según manual
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(consultaRequest)
                    .retrieve()
                    .bodyToMono(ConsultaRecargaResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta de servicio: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de servicio: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta de servicio", e);
            throw new RuntimeException("Error al consultar servicio");
        }
    }

    public ConsultaRecargaResponse consultarReferencia(ConsultaRecargaRequest consultaRequest, String token) {
        try {
            return webClient.post()
                    .uri("/emisores/consultaReferencia") // Ruta corregida según manual
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(consultaRequest)
                    .retrieve()
                    .bodyToMono(ConsultaRecargaResponse.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error en consulta de referencia: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error en consulta de referencia: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado en consulta de referencia", e);
            throw new RuntimeException("Error al consultar referencia");
        }
    }

    public SaldoResponse consultarSaldo(SaldoRequest saldoRequest, String token) {
        try {
            return webClient.post()
                    .uri("/emisores/saldo") // Ruta corregida según manual
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(saldoRequest)
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
                    .uri("/emisores/productos") // Ruta corregida según manual
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

    // Método para realizar consultas con retry según el manual (códigos -600 y 71)
    public ConsultaRecargaResponse consultarServicioConReintentos(ConsultaRecargaRequest consultaRequest, String token, int maxIntentos) {
        int intentos = 0;

        while (intentos < maxIntentos) {
            try {
                if (intentos > 0) {
                    Thread.sleep(5000); // 5 segundos entre intentos según manual
                }

                ConsultaRecargaResponse response = consultarServicio(consultaRequest, token);

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

    // Método auxiliar para generar fecha/hora actual
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DATE_FORMAT);
    }

    // Métodos de conveniencia para el controlador
    public PagoServicioResponse pagarServicio(PagoServicioRequest pagoRequest, AuthRequest authRequest) {
        AuthResponse authResponse = authenticate(authRequest);
        return pagarServicio(pagoRequest, authResponse.getToken());
    }

    public ConsultaRecargaResponse consultarServicio(ConsultaRecargaRequest consultaRequest, AuthRequest authRequest) {
        AuthResponse authResponse = authenticate(authRequest);
        return consultarServicio(consultaRequest, authResponse.getToken());
    }

    public ConsultaRecargaResponse consultarServicioConReintentos(ConsultaRecargaRequest consultaRequest, AuthRequest authRequest) {
        AuthResponse authResponse = authenticate(authRequest);
        return consultarServicioConReintentos(consultaRequest, authResponse.getToken(), 8); // 8 intentos según manual
    }

    public ConsultaRecargaResponse consultarReferencia(ConsultaRecargaRequest consultaRequest, AuthRequest authRequest) {
        AuthResponse authResponse = authenticate(authRequest);
        return consultarReferencia(consultaRequest, authResponse.getToken());
    }

    public SaldoResponse consultarSaldo(SaldoRequest saldoRequest, AuthRequest authRequest) {
        AuthResponse authResponse = authenticate(authRequest);
        return consultarSaldo(saldoRequest, authResponse.getToken());
    }

    public ProductosResponse consultarProductos(AuthRequest authRequest) {
        AuthResponse authResponse = authenticate(authRequest);
        return consultarProductos(authResponse.getToken());
    }
}
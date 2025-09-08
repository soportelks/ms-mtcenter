package com.lksbaas.mx.controller;

import com.lksbaas.mx.dto.auth.AuthRequest;
import com.lksbaas.mx.dto.auth.AuthResponse;
import com.lksbaas.mx.dto.servicios.*;
import com.lksbaas.mx.service.AuthService;
import com.lksbaas.mx.service.MTCenterServiciosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mtcenter/servicios")
public class MTCenterServiciosController {

    private final AuthService authService;
    private final MTCenterServiciosService mtCenterService;
    private static final Logger log = LoggerFactory.getLogger(MTCenterServiciosController.class);

    public MTCenterServiciosController(MTCenterServiciosService mtCenterService, AuthService authService) {
        this.mtCenterService = mtCenterService;
        this.authService = authService;
    }

    private String obtenerToken(AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        if (authResponse != null && authResponse.getToken() != null) {
            return authResponse.getToken();
        } else {
            throw new RuntimeException("No se pudo obtener el token de autenticación");
        }
    }

    /**
     * Endpoint para realizar pago de servicio
     * POST /api/mtcenter/pago
     */
    @PostMapping("/pago")
    public ResponseEntity<?> pagarServicio(
            @RequestBody Map<String, Object> requestData) {
        try {
            // Extraer datos de autenticación
            AuthRequest authRequest = new AuthRequest(
                    (Integer) requestData.get("cadena"),
                    (Integer) requestData.get("establecimiento"),
                    (Integer) requestData.get("terminal"),
                    (Integer) requestData.get("cajero"),
                    (String) requestData.get("clave")
            );

            // Obtener token
            String token = obtenerToken(authRequest);

            // Extraer datos de la recarga
            PagoServicioRequest pagoRequest = new PagoServicioRequest(
                    (String) requestData.get("referencia1"),
                    (String) requestData.get("referencia2"),
                    (String) requestData.get("sku"),
                    (Integer) requestData.get("no_transaccion"),
                    (String) requestData.getOrDefault("fecha_hora", mtCenterService.getCurrentDateTime()),
                    ((Number) requestData.get("monto")).doubleValue()
            );

            log.info("Realizando pago para referencia: {}", pagoRequest.getReferencia1());
            PagoServicioResponse response = mtCenterService.pagarServicio(pagoRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en pago de servicio", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al procesar pago de servicio", "message", e.getMessage()));
        }
    }

    /**
     * Endpoint para consultar estado de servicio
     * POST /api/mtcenter/consulta
     */
    @PostMapping("/consulta")
    public ResponseEntity<?> consultarServicio(
            @RequestBody Map<String, Object> requestData) {
        try {
            // Extraer datos de autenticación
            AuthRequest authRequest = new AuthRequest(
                    (Integer) requestData.get("cadena"),
                    (Integer) requestData.get("establecimiento"),
                    (Integer) requestData.get("terminal"),
                    (Integer) requestData.get("cajero"),
                    (String) requestData.get("clave")
            );

            // Obtener token
            String token = obtenerToken(authRequest);

            // Extraer datos de la consulta
            ConsultaRecargaRequest consultaRequest = new ConsultaRecargaRequest(
                    (String) requestData.get("referencia1"),
                    (String) requestData.get("sku"),
                    (Integer) requestData.get("no_transaccion"),
                    (String) requestData.getOrDefault("fecha_hora", mtCenterService.getCurrentDateTime())
            );

            log.info("Consultando servicio para referencia: {}", consultaRequest.getReferencia1());
            ConsultaRecargaResponse response = mtCenterService.consultarServicio(consultaRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de servicio", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al consultar servicio", "message", e.getMessage()));
        }
    }

    /**
     * Endpoint para consultar servicio con reintentos automáticos
     * POST /api/mtcenter/consulta-reintentos
     */
    @PostMapping("/consulta-reintentos")
    public ResponseEntity<?> consultarServicioConReintentos(
            @RequestBody Map<String, Object> requestData) {
        try {
            // Extraer datos de autenticación
            AuthRequest authRequest = new AuthRequest(
                    (Integer) requestData.get("cadena"),
                    (Integer) requestData.get("establecimiento"),
                    (Integer) requestData.get("terminal"),
                    (Integer) requestData.get("cajero"),
                    (String) requestData.get("clave")
            );

            // Obtener token
            String token = obtenerToken(authRequest);

            // Extraer datos de la consulta
            ConsultaRecargaRequest consultaRequest = new ConsultaRecargaRequest(
                    (String) requestData.get("referencia1"),
                    (String) requestData.get("sku"),
                    (Integer) requestData.get("no_transaccion"),
                    (String) requestData.getOrDefault("fecha_hora", mtCenterService.getCurrentDateTime())
            );

            log.info("Consultando servicio con reintentos para referencia: {}", consultaRequest.getReferencia1());
            ConsultaRecargaResponse response = mtCenterService.consultarServicioConReintentos(consultaRequest, token, 8);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de servicio con reintentos", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al consultar servicio con reintentos", "message", e.getMessage()));
        }
    }

    /**
     * Endpoint para consultar referencia
     * POST /api/mtcenter/consulta-referencia
     */
    @PostMapping("/consulta-referencia")
    public ResponseEntity<?> consultarReferencia(
            @RequestBody Map<String, Object> requestData) {
        try {
            // Extraer datos de autenticación
            AuthRequest authRequest = new AuthRequest(
                    (Integer) requestData.get("cadena"),
                    (Integer) requestData.get("establecimiento"),
                    (Integer) requestData.get("terminal"),
                    (Integer) requestData.get("cajero"),
                    (String) requestData.get("clave")
            );

            // Obtener token
            String token = obtenerToken(authRequest);

            // Extraer datos de la consulta
            ConsultaRecargaRequest consultaRequest = new ConsultaRecargaRequest();
            consultaRequest.setReferencia1((String) requestData.get("referencia1"));
            consultaRequest.setSku((String) requestData.get("sku"));

            log.info("Consultando referencia: {}", consultaRequest.getReferencia1());
            ConsultaRecargaResponse response = mtCenterService.consultarReferencia(consultaRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de referencia", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al consultar referencia", "message", e.getMessage()));
        }
    }

    /**
     * Endpoint para consultar saldo
     * POST /api/mtcenter/saldo
     */
    @PostMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(
            @RequestBody Map<String, Object> requestData) {
        try {
            // Extraer datos de autenticación
            AuthRequest authRequest = new AuthRequest(
                    (Integer) requestData.get("cadena"),
                    (Integer) requestData.get("establecimiento"),
                    (Integer) requestData.get("terminal"),
                    (Integer) requestData.get("cajero"),
                    (String) requestData.get("clave")
            );

            // Obtener token
            String token = obtenerToken(authRequest);

            // Crear request de saldo
            SaldoRequest saldoRequest = new SaldoRequest(
                    (String) requestData.getOrDefault("fecha_hora", mtCenterService.getCurrentDateTime())
            );

            log.info("Consultando saldo");
            SaldoResponse response = mtCenterService.consultarSaldo(saldoRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de saldo", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al consultar saldo", "message", e.getMessage()));
        }
    }

    /**
     * Endpoint para consultar productos disponibles
     * POST /api/mtcenter/productos
     */
    @PostMapping("/productos")
    public ResponseEntity<?> consultarProductos(
            @RequestBody Map<String, Object> requestData) {
        try {
            // Extraer datos de autenticación
            AuthRequest authRequest = new AuthRequest(
                    (Integer) requestData.get("cadena"),
                    (Integer) requestData.get("establecimiento"),
                    (Integer) requestData.get("terminal"),
                    (Integer) requestData.get("cajero"),
                    (String) requestData.get("clave")
            );

            // Obtener token
            String token = obtenerToken(authRequest);

            log.info("Consultando productos disponibles");
            ProductosResponse response = mtCenterService.consultarProductos(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de productos", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al consultar productos", "message", e.getMessage()));
        }
    }

    /**
     * Endpoint para obtener la fecha y hora actual en formato MTCenter
     * GET /api/mtcenter/datetime
     */
    @GetMapping("/datetime")
    public ResponseEntity<?> getCurrentDateTime() {
        try {
            String dateTime = mtCenterService.getCurrentDateTime();
            return ResponseEntity.ok(Map.of("fecha_hora", dateTime));
        } catch (Exception e) {
            log.error("Error al obtener fecha y hora", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al obtener fecha y hora", "message", e.getMessage()));
        }
    }
}
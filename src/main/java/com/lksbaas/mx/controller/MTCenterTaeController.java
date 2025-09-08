package com.lksbaas.mx.controller;

import com.lksbaas.mx.dto.auth.AuthRequest;
import com.lksbaas.mx.dto.auth.AuthResponse;
import com.lksbaas.mx.dto.tae.*;
import com.lksbaas.mx.service.AuthService;
import com.lksbaas.mx.service.MTCenterTaeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/mtcenter/tae")
public class MTCenterTaeController {

    private final AuthService authService;
    private final MTCenterTaeService mtCenterService;
    private final AtomicInteger transactionCounter = new AtomicInteger(1);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Autowired
    public MTCenterTaeController(MTCenterTaeService mtCenterService, AuthService authService) {
        this.mtCenterService = mtCenterService;
        this.authService = authService;
    }

    /**
     * Endpoint para realizar recarga TAE
     * POST /api/mtcenter/tae/recarga
     */
    @PostMapping("/recarga")
    public ResponseEntity<?> realizarRecarga(@RequestBody Map<String, Object> request) {
        try {
            // Extraer credenciales del request
            AuthRequest credenciales = extractCredentials(request);
            String token = mtCenterService.getValidToken(credenciales);

            // Crear request de recarga
            RecargaRequest recargaRequest = new RecargaRequest(
                    (String) request.get("telefono"),
                    (String) request.get("sku"),
                    getTransactionId(),
                    getCurrentDateTime(),
                    (String) request.get("cve_estado")
            );

            RecargaResponse response = mtCenterService.realizarRecarga(recargaRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al realizar recarga",
                            "details", e.getMessage()));
        }
    }

    /**
     * Endpoint para consultar saldo
     * POST /api/mtcenter/tae/saldo
     */
    @PostMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@RequestBody AuthRequest credenciales) {
        try {
            String token = mtCenterService.getValidToken(credenciales);
            SaldoResponse response = mtCenterService.consultarSaldo(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al consultar saldo",
                            "details", e.getMessage()));
        }
    }

    /**
     * Endpoint para consultar una recarga específica
     * POST /api/mtcenter/tae/consulta-recarga
     */
    @PostMapping("/consulta-recarga")
    public ResponseEntity<?> consultarRecarga(@RequestBody Map<String, Object> request) {
        try {
            // Extraer credenciales del request
            AuthRequest credenciales = extractCredentials(request);
            String token = mtCenterService.getValidToken(credenciales);

            // Crear request de consulta
            ConsultaRecargaRequest consultaRequest = new ConsultaRecargaRequest(
                    (String) request.get("telefono"),
                    (String) request.get("sku"),
                    (Integer) request.get("no_transaccion_recarga"),
                    (String) request.get("fecha_hora_recarga"),
                    getTransactionId(),
                    getCurrentDateTime()
            );

            ConsultaRecargaResponse response = mtCenterService.consultarRecarga(token, consultaRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al consultar recarga",
                            "details", e.getMessage()));
        }
    }

    /**
     * Endpoint para consultar recarga con reintentos automáticos
     * POST /api/mtcenter/tae/consulta-recarga-reintentos
     */
    @PostMapping("/consulta-recarga-reintentos")
    public ResponseEntity<?> consultarRecargaConReintentos(@RequestBody Map<String, Object> request) {
        try {
            // Extraer credenciales del request
            AuthRequest credenciales = extractCredentials(request);
            String token = mtCenterService.getValidToken(credenciales);

            // Crear request de consulta
            ConsultaRecargaRequest consultaRequest = new ConsultaRecargaRequest(
                    (String) request.get("telefono"),
                    (String) request.get("sku"),
                    (Integer) request.get("no_transaccion_recarga"),
                    (String) request.get("fecha_hora_recarga"),
                    getTransactionId(),
                    getCurrentDateTime()
            );

            ConsultaRecargaResponse response = mtCenterService.consultarRecargaConReintentos(token, consultaRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al consultar recarga con reintentos",
                            "details", e.getMessage()));
        }
    }

    /**
     * Endpoint para obtener productos disponibles
     * POST /api/mtcenter/tae/productos
     */
    @PostMapping("/productos")
    public ResponseEntity<?> obtenerProductos(@RequestBody AuthRequest credenciales) {
        try {
            String token = mtCenterService.getValidToken(credenciales);
            ProductosResponse response = mtCenterService.obtenerProductos(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al obtener productos",
                            "details", e.getMessage()));
        }
    }

    /**
     * Endpoint para obtener productos disponibles (GET alternativo)
     * GET /api/mtcenter/tae/productos/{cadena}/{establecimiento}/{terminal}/{cajero}
     */
    @GetMapping("/productos/{cadena}/{establecimiento}/{terminal}/{cajero}")
    public ResponseEntity<?> obtenerProductosGet(@PathVariable Integer cadena,
                                                 @PathVariable Integer establecimiento,
                                                 @PathVariable Integer terminal,
                                                 @PathVariable Integer cajero,
                                                 @RequestParam String clave) {
        try {
            AuthRequest credenciales = new AuthRequest(cadena, establecimiento, terminal, cajero, clave);
            String token = mtCenterService.getValidToken(credenciales);
            ProductosResponse response = mtCenterService.obtenerProductos(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error al obtener productos",
                            "details", e.getMessage()));
        }
    }

    /**
     * Endpoint de prueba de conectividad
     * GET /api/mtcenter/tae/health
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "service", "MTCenter TAE Integration",
                "timestamp", getCurrentDateTime()
        ));
    }

    /**
     * Endpoint para proceso completo: recarga y consulta con reintentos
     * POST /api/mtcenter/tae/recarga-completa
     */
    @PostMapping("/recarga-completa")
    public ResponseEntity<?> recargaCompleta(@RequestBody Map<String, Object> request) {
        try {
            // Extraer credenciales del request
            AuthRequest credenciales = extractCredentials(request);
            String token = mtCenterService.getValidToken(credenciales);

            // Realizar recarga
            RecargaRequest recargaRequest = new RecargaRequest(
                    (String) request.get("telefono"),
                    (String) request.get("sku"),
                    getTransactionId(),
                    getCurrentDateTime(),
                    (String) request.get("cve_estado")
            );

            RecargaResponse recargaResponse = mtCenterService.realizarRecarga(recargaRequest, token);

            // Si la recarga no fue exitosa inmediatamente, iniciar consultas
            if (recargaResponse.getCodigoRespuesta() != 0) {
                // Esperar 30 segundos antes de la primera consulta
                Thread.sleep(30000);

                ConsultaRecargaRequest consultaRequest = new ConsultaRecargaRequest(
                        recargaRequest.getTelefono(),
                        recargaRequest.getSku(),
                        recargaRequest.getNoTransaccion(),
                        recargaRequest.getFechaHora(),
                        getTransactionId(),
                        getCurrentDateTime()
                );

                ConsultaRecargaResponse consultaResponse = mtCenterService.consultarRecargaConReintentos(token, consultaRequest);

                return ResponseEntity.ok(Map.of(
                        "recarga_inicial", recargaResponse,
                        "consulta_final", consultaResponse,
                        "status", consultaResponse.getCodigoRespuesta() == 0 ? "SUCCESS" : "FAILED"
                ));
            }

            return ResponseEntity.ok(Map.of(
                    "recarga_inicial", recargaResponse,
                    "status", "SUCCESS"
            ));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Proceso interrumpido",
                            "details", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error en proceso completo de recarga",
                            "details", e.getMessage()));
        }
    }

    /**
     * Extrae las credenciales del request Map
     */
    private AuthRequest extractCredentials(Map<String, Object> request) {
        return new AuthRequest(
                (Integer) request.get("cadena"),
                (Integer) request.get("establecimiento"),
                (Integer) request.get("terminal"),
                (Integer) request.get("cajero"),
                (String) request.get("clave")
        );
    }

    /**
     * Genera un ID de transacción único y secuencial
     */
    private Integer getTransactionId() {
        return transactionCounter.getAndIncrement();
    }

    /**
     * Obtiene la fecha y hora actual en el formato requerido por MTCenter
     */
    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DATE_FORMAT);
    }
}
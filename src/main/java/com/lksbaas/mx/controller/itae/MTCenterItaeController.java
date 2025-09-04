package com.lksbaas.mx.controller.itae;

import com.lksbaas.mx.dto.itae.*;
import com.lksbaas.mx.service.itae.MTCenterItaeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/mtcenter")
public class MTCenterItaeController {

    private final MTCenterItaeService mtCenterService;
    private static final Logger log = LoggerFactory.getLogger(MTCenterItaeController.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Constructor con inyección de dependencia
    public MTCenterItaeController(MTCenterItaeService mtCenterService) {
        this.mtCenterService = mtCenterService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        try {
            log.info("Iniciando autenticación para cadena: {}, establecimiento: {}",
                    request.getCadena(), request.getEstablecimiento());
            AuthResponse response = mtCenterService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en autenticación", e);
            return ResponseEntity.status(500).body("Error al autenticar: " + e.getMessage());
        }
    }

    @PostMapping("/recarga")
    public ResponseEntity<?> realizarRecarga(@RequestBody RecargaRequest request,
                                             @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Realizando recarga para teléfono: {} por importe: {}",
                    request.getTelefono(), request.getImporte());

            RecargaResponse response = mtCenterService.realizarRecarga(request, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en recarga", e);
            return ResponseEntity.status(500).body("Error al realizar recarga: " + e.getMessage());
        }
    }

    @GetMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando saldo");

            SaldoResponse response = mtCenterService.consultarSaldo(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de saldo", e);
            return ResponseEntity.status(500).body("Error al consultar saldo: " + e.getMessage());
        }
    }

    @PostMapping("/consulta-recarga")
    public ResponseEntity<?> consultarRecarga(@RequestBody ConsultaRecargaRequest request,
                                              @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando recarga para transacción: {}", request.getNoTransaccionRecarga());

            ConsultaRecargaResponse response = mtCenterService.consultarRecarga(request, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de recarga", e);
            return ResponseEntity.status(500).body("Error al consultar recarga: " + e.getMessage());
        }
    }

    @PostMapping("/consulta-recarga-retry")
    public ResponseEntity<?> consultarRecargaConRetry(@RequestBody ConsultaRecargaRequest request,
                                                      @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando recarga con retry para transacción: {}", request.getNoTransaccionRecarga());

            ConsultaRecargaResponse response = mtCenterService.consultarRecargaConRetry(request, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de recarga con retry", e);
            return ResponseEntity.status(500).body("Error al consultar recarga: " + e.getMessage());
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<?> consultarProductos(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando productos disponibles");

            ProductosResponse response = mtCenterService.consultarProductos(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de productos", e);
            return ResponseEntity.status(500).body("Error al consultar productos: " + e.getMessage());
        }
    }

    // Endpoint de conveniencia para generar formato de fecha
    @GetMapping("/fecha-actual")
    public ResponseEntity<String> obtenerFechaActual() {
        return ResponseEntity.ok(LocalDateTime.now().format(DATE_FORMAT));
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Token de autorización inválido");
    }
}
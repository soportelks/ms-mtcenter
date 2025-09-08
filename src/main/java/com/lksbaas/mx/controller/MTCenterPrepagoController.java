package com.lksbaas.mx.controller;

import com.lksbaas.mx.dto.auth.AuthRequest;
import com.lksbaas.mx.dto.auth.AuthResponse;
import com.lksbaas.mx.dto.prepago.*;
import com.lksbaas.mx.service.AuthService;
import com.lksbaas.mx.service.MTCenterPrepagoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/mtcenter/prepago")
public class MTCenterPrepagoController {

    private final AuthService authService;
    private final MTCenterPrepagoService prepagoService;
    private static final Logger log = LoggerFactory.getLogger(MTCenterPrepagoController.class);

    public MTCenterPrepagoController(MTCenterPrepagoService prepagoService, AuthService authService) {
        this.prepagoService = prepagoService;
        this.authService = authService;
    }

    @PostMapping("/prepago")
    public ResponseEntity<?> realizarRecargaPrepago(@RequestBody RecargaPrepagoRequest recargaRequest,
                                                    @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Realizando recarga prepago");
            RecargaPrepagoResponse response = prepagoService.realizarRecargaPrepago(recargaRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en recarga prepago", e);
            return ResponseEntity.status(500).body("Error al realizar recarga: " + e.getMessage());
        }
    }

    @PostMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@RequestBody ConsultaSaldoRequest saldoRequest,
                                            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando saldo");
            ConsultaSaldoResponse response = prepagoService.consultarSaldo(saldoRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de saldo", e);
            return ResponseEntity.status(500).body("Error al consultar saldo: " + e.getMessage());
        }
    }

    @PostMapping("/consulta")
    public ResponseEntity<?> consultarPrepago(@RequestBody ConsultaPrepagoRequest consultaRequest,
                                              @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando recarga prepago");
            ConsultaPrepagoResponse response = prepagoService.consultarPrepago(consultaRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de recarga", e);
            return ResponseEntity.status(500).body("Error al consultar recarga: " + e.getMessage());
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<?> consultarProductos(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando productos");
            ProductosResponse response = prepagoService.consultarProductos(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de productos", e);
            return ResponseEntity.status(500).body("Error al consultar productos: " + e.getMessage());
        }
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Token de autorización inválido");
    }

    @PostMapping("/consulta-con-reintentos")
    public ResponseEntity<?> consultarPrepagoConReintentos(@RequestBody ConsultaPrepagoRequest consultaRequest,
                                                           @RequestHeader("Authorization") String authHeader,
                                                           @RequestParam(defaultValue = "8") int maxIntentos) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando recarga prepago con reintentos, máximo intentos: {}", maxIntentos);
            ConsultaPrepagoResponse response = prepagoService.consultarPrepagoConReintentos(consultaRequest, token, maxIntentos);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de recarga con reintentos", e);
            return ResponseEntity.status(500).body("Error al consultar recarga: " + e.getMessage());
        }
    }

    @GetMapping("/saldo-actual")
    public ResponseEntity<?> consultarSaldoActual(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando saldo actual");

            // Crear request con fecha actual usando setter
            String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            ConsultaSaldoRequest request = new ConsultaSaldoRequest();
            request.setFecha_hora(fechaActual); // o el setter correspondiente según el nombre del campo

            ConsultaSaldoResponse response = prepagoService.consultarSaldo(request, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de saldo actual", e);
            return ResponseEntity.status(500).body("Error al consultar saldo actual: " + e.getMessage());
        }
    }
}
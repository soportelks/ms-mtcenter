package com.lksbaas.mx.controller;

import com.lksbaas.mx.dto.prepago.*;
import com.lksbaas.mx.service.MTCenterPrepagoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prepago")
public class MTCenterPrepagoController {

    private final MTCenterPrepagoService prepagoService;
    private static final Logger log = LoggerFactory.getLogger(MTCenterPrepagoController.class);

    public MTCenterPrepagoController(MTCenterPrepagoService prepagoService) {
        this.prepagoService = prepagoService;
    }

    @PostMapping("/token/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            log.info("Iniciando autenticación");
            AuthResponse response = prepagoService.authenticate(authRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en autenticación", e);
            return ResponseEntity.status(500).body("Error al autenticar con MTCenter: " + e.getMessage());
        }
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
}
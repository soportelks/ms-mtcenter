package com.lksbaas.mx.controller;

import com.lksbaas.mx.dto.auth.AuthRequest;
import com.lksbaas.mx.dto.auth.AuthResponse;
import com.lksbaas.mx.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);

    public AuthApiController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        try {
            log.info("Autenticando para cadena: {}, establecimiento: {}",
                    request.getCadena(), request.getEstablecimiento());
            AuthResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en autenticación", e);
            return ResponseEntity.status(500)
                    .body("Error al autenticar: " + e.getMessage());
        }
    }
}
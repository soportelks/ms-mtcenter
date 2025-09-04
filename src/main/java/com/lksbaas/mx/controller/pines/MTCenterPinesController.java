package com.lksbaas.mx.controller.pines;

import com.lksbaas.mx.dto.pines.*;
import com.lksbaas.mx.service.pines.MTCenterPinesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/mtcenter/pines")
public class MTCenterPinesController {

    private final MTCenterPinesService mtCenterService;
    private static final Logger log = LoggerFactory.getLogger(MTCenterPinesController.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Constructor con inyección de dependencia
    public MTCenterPinesController(MTCenterPinesService mtCenterService) {
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
            log.info("Realizando recarga de PIN para SKU: {} con transacción: {}",
                    request.getSku(), request.getNoTransaccion());

            RecargaResponse response = mtCenterService.recargarPin(request, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en recarga de PIN", e);
            return ResponseEntity.status(500).body("Error al realizar recarga: " + e.getMessage());
        }
    }

    @PostMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@RequestBody SaldoRequest request,
                                            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando saldo con fecha: {}", request.getFechaHora());

            SaldoResponse response = mtCenterService.consultarSaldo(request, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de saldo", e);
            return ResponseEntity.status(500).body("Error al consultar saldo: " + e.getMessage());
        }
    }

    @GetMapping("/saldo-actual")
    public ResponseEntity<?> consultarSaldoActual(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando saldo actual");

            SaldoResponse response = mtCenterService.consultarSaldoActual(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de saldo actual", e);
            return ResponseEntity.status(500).body("Error al consultar saldo actual: " + e.getMessage());
        }
    }

    @PostMapping("/consulta-recarga")
    public ResponseEntity<?> consultarRecarga(@RequestBody ConsultaRecargaRequest request,
                                              @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando recarga para transacción: {}", request.getNoTransaccion());

            ConsultaRecargaResponse response = mtCenterService.consultarRecarga(request, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de recarga", e);
            return ResponseEntity.status(500).body("Error al consultar recarga: " + e.getMessage());
        }
    }

    @PostMapping("/consulta-recarga-reintentos")
    public ResponseEntity<?> consultarRecargaConReintentos(@RequestBody ConsultaRecargaRequest request,
                                                           @RequestHeader("Authorization") String authHeader,
                                                           @RequestParam(defaultValue = "3") int maxIntentos) {
        try {
            String token = extractToken(authHeader);
            log.info("Consultando recarga con reintentos para transacción: {}, máximo intentos: {}",
                    request.getNoTransaccion(), maxIntentos);

            ConsultaRecargaResponse response = mtCenterService.consultarRecargaConReintentos(request, token, maxIntentos);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en consulta de recarga con reintentos", e);
            return ResponseEntity.status(500).body("Error al consultar recarga con reintentos: " + e.getMessage());
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

    // Endpoints de conveniencia para crear requests
    @PostMapping("/crear-recarga")
    public ResponseEntity<?> crearRecargaRequest(@RequestParam String sku,
                                                 @RequestParam Integer noTransaccion,
                                                 @RequestParam String referencia1,
                                                 @RequestParam String referencia2) {
        try {
            RecargaRequest request = mtCenterService.crearRecargaRequest(sku, noTransaccion, referencia1, referencia2);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            log.error("Error al crear request de recarga", e);
            return ResponseEntity.status(500).body("Error al crear request: " + e.getMessage());
        }
    }

    @PostMapping("/crear-consulta-recarga")
    public ResponseEntity<?> crearConsultaRecargaRequest(@RequestParam String referencia1,
                                                         @RequestParam String referencia2,
                                                         @RequestParam String sku,
                                                         @RequestParam Integer noTransaccionPin,
                                                         @RequestParam String fechaHoraPin,
                                                         @RequestParam Integer noTransaccion) {
        try {
            ConsultaRecargaRequest request = mtCenterService.crearConsultaRecargaRequest(
                    referencia1, referencia2, sku, noTransaccionPin, fechaHoraPin, noTransaccion);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            log.error("Error al crear request de consulta de recarga", e);
            return ResponseEntity.status(500).body("Error al crear request: " + e.getMessage());
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

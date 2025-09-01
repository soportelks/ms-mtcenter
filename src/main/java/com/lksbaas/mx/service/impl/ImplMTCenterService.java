/**
 * Implementación del servicio MTCenter con lógica de negocio y manejo de reintentos.
 *
 * Proporciona la capa de servicio que orquesta las operaciones del DAO con
 * lógica adicional como generación de transacciones, manejo de timeouts y
 * mecanismos de reintentos para consultas de estado.
 */
package com.lksbaas.mx.service.impl;

import com.lksbaas.mx.dao.IMTCenterDAO;
import com.lksbaas.mx.model.ConsultaRecarga;
import com.lksbaas.mx.model.ProductosMTCenter;
import com.lksbaas.mx.model.RecargaInternacional;
import com.lksbaas.mx.model.SaldoMTCenter;
import com.lksbaas.mx.service.IMTCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ImplMTCenterService implements IMTCenterService {

    private static final Logger logger = LoggerFactory.getLogger(ImplMTCenterService.class);

    @Autowired
    private IMTCenterDAO mtCenterDAO;

    // ============ MÉTODOS DE LA INTERFAZ ============

    /**
     * Realiza una recarga internacional con gestión completa de la transacción.
     * Implementa el patrón de "fire and forget" con verificación posterior.
     *
     * Flujo:
     * 1. Genera número de transacción y timestamp
     * 2. Registra el inicio de la operación
     * 3. Intenta la recarga inmediata
     * 4. Si hay error o timeout, inicia proceso de consultas
     *
     * @param recarga Datos de la recarga a realizar
     * @return Resultado de la recarga o de las consultas posteriores
     */
    @Override
    public RecargaInternacional realizarRecarga(RecargaInternacional recarga) {
        try {
            // Generar número de transacción único basado en timestamp
            recarga.setNoTransaccion(generateTransactionNumber());
            // Establecer timestamp actual en formato dd/MM/yyyy HH:mm:ss
            recarga.setFechaHora(LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            ));

            logger.info("Iniciando recarga para teléfono: {}, monto: {}",
                    recarga.getTelefono(), recarga.getImporte());

            // Intentar recarga inmediata
            RecargaInternacional resultado = mtCenterDAO.realizarRecarga(recarga);

            if (resultado == null) {
                // Si no hay respuesta, iniciar proceso de consultas asíncronas
                logger.warn("No se recibió respuesta, iniciando proceso de consultas");
                return consultarRecargaConReintentos(recarga);
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Error en servicio de recarga", e);
            // En caso de timeout o error, iniciar consultas de verificación
            return consultarRecargaConReintentos(recarga);
        }
    }

    /**
     * Consulta el estado de una recarga con mecanismo de reintentos inteligente.
     * Diseñado para manejar transacciones asíncronas en MTCenter.
     *
     * Estrategia de reintentos:
     * - Espera inicial: 30 segundos (tiempo típico de procesamiento)
     * - Máximo intentos: 8 consultas
     * - Intervalo entre consultas: 5 segundos
     * - Tiempo total máximo: 60 segundos (30s inicial + 7*5s = 65s máximo teórico)
     *
     * Códigos de respuesta manejados:
     * - 0: Éxito → Detener consultas
     * - -600: Transacción en proceso → Continuar consultas
     * - 71: Transacción en proceso → Continuar consultas
     * - Otros: Error definitivo → Detener consultas
     *
     * @param recargaOriginal Datos originales de la transacción a consultar
     * @return Resultado final de la transacción
     */
    @Override
    public RecargaInternacional consultarRecargaConReintentos(RecargaInternacional recargaOriginal) {
        logger.info("Iniciando proceso de consultas para transacción: {}",
                recargaOriginal.getNoTransaccion());

        try {
            // Espera inicial de 30 segundos (tiempo estimado de procesamiento)
            Thread.sleep(30000);

            int intentos = 0;
            long tiempoInicio = System.currentTimeMillis();
            long tiempoLimite = 60000; // 60 segundos total para consultas

            // Realizar hasta 8 intentos dentro del tiempo límite
            while (intentos < 8 && (System.currentTimeMillis() - tiempoInicio) < tiempoLimite) {
                try {
                    // Preparar objeto de consulta con datos originales
                    ConsultaRecarga consulta = new ConsultaRecarga();
                    consulta.setTelefono(recargaOriginal.getTelefono());
                    consulta.setSku(recargaOriginal.getSku());
                    consulta.setNoTransaccionRecarga(recargaOriginal.getNoTransaccion());
                    consulta.setFechaHoraRecarga(recargaOriginal.getFechaHora());
                    consulta.setNoTransaccion(generateTransactionNumber());
                    consulta.setFechaHora(LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    ));

                    // Realizar consulta al DAO
                    RecargaInternacional resultado = mtCenterDAO.consultarRecarga(consulta);

                    logger.info("Consulta intento {}: código {}", intentos + 1,
                            resultado.getCodigoRespuesta());

                    // Si es exitosa (código 0), detener consultas
                    if (resultado.getCodigoRespuesta() == 0) {
                        logger.info("Transacción exitosa encontrada");
                        return resultado;
                    }

                    // Si es diferente de -600 o 71, es un error definitivo
                    if (resultado.getCodigoRespuesta() != -600 &&
                            resultado.getCodigoRespuesta() != 71) {
                        logger.info("Error definitivo encontrado: {}",
                                resultado.getCodigoRespuesta());
                        return resultado;
                    }

                    intentos++;
                    if (intentos < 8) {
                        Thread.sleep(5000); // Esperar 5 segundos entre intentos
                    }

                } catch (Exception e) {
                    logger.error("Error en consulta intento " + (intentos + 1), e);
                    break;
                }
            }

            // Si se agotó el tiempo o intentos, crear respuesta de error
            RecargaInternacional errorResponse = new RecargaInternacional();
            errorResponse.setTelefono(recargaOriginal.getTelefono());
            errorResponse.setImporte(recargaOriginal.getImporte());
            errorResponse.setCodigoRespuesta(-1);
            errorResponse.setDescripcionRespuesta("Transacción no encontrada después de consultas");

            return errorResponse;

        } catch (InterruptedException e) {
            // Restaurar estado de interrupción y lanzar excepción
            Thread.currentThread().interrupt();
            logger.error("Proceso de consultas interrumpido", e);
            throw new RuntimeException("Proceso de consultas interrumpido", e);
        }
    }

    /**
     * Consulta el saldo disponible delegando al DAO.
     * Sin lógica adicional de negocio.
     *
     * @return SaldoMTCenter con la información actual del saldo
     */
    @Override
    public SaldoMTCenter consultarSaldo() {
        return mtCenterDAO.consultarSaldo();
    }

    /**
     * Consulta los productos disponibles delegando al DAO.
     * Sin lógica adicional de negocio.
     *
     * @return ProductosMTCenter con el catálogo de productos
     */
    @Override
    public ProductosMTCenter consultarProductos() {
        return mtCenterDAO.consultarProductos();
    }

    // ============ MÉTODOS PRIVADOS ============

    private Integer generateTransactionNumber() {
        return (int) (System.currentTimeMillis() % 99999);
    }
}
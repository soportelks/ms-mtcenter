/**
 * Interfaz de servicio para operaciones de negocio con MTCenter.
 *
 * Define las operaciones de alto nivel que exponen la funcionalidad de MTCenter
 * a los controladores o otros servicios de la aplicación. Incluye lógica de
 * negocio como reintentos y manejo de transacciones.
 */
package com.lksbaas.mx.service;

import com.lksbaas.mx.model.ProductosMTCenter;
import com.lksbaas.mx.model.RecargaInternacional;
import com.lksbaas.mx.model.SaldoMTCenter;

public interface IMTCenterService {

    /**
     * Realiza una recarga internacional con gestión completa de la transacción.
     * Incluye generación de número de transacción, timestamp y manejo de errores.
     *
     * @param recarga Objeto RecargaInternacional con los datos de la recarga:
     *                - telefono: Número internacional destino
     *                - importe: Monto de la recarga
     *                - countryCode: Código de país ISO
     *                - mobileCountryCode: Código MCC
     *                - sku: Identificador del producto
     *                (noTransaccion y fechaHora se generan automáticamente)
     * @return RecargaInternacional con la respuesta de la recarga, que puede ser:
     *         - Respuesta inmediata del servicio
     *         - Resultado de consultas posteriores
     *         - Respuesta de error después de reintentos
     * @throws RuntimeException si ocurre un error no manejado
     */
    RecargaInternacional realizarRecarga(RecargaInternacional recarga);

    /**
     * Consulta el saldo disponible en la cuenta MTCenter.
     * Delega la operación al DAO sin lógica adicional de negocio.
     *
     * @return SaldoMTCenter con la información actualizada del saldo
     * @throws RuntimeException si ocurre un error en la consulta
     */
    SaldoMTCenter consultarSaldo();

    /**
     * Consulta el catálogo completo de productos disponibles.
     * Delega la operación al DAO sin lógica adicional de negocio.
     *
     * @return ProductosMTCenter con la estructura jerárquica de productos
     * @throws RuntimeException si ocurre un error en la consulta
     */
    ProductosMTCenter consultarProductos();

    /**
     * Consulta el estado de una recarga con mecanismo de reintentos.
     * Implementa un proceso robusto de consulta para verificar transacciones
     * que pueden tener procesamiento asíncrono en MTCenter.
     *
     * @param recargaOriginal Objeto RecargaInternacional original con los datos
     *                        de la transacción a consultar
     * @return RecargaInternacional con el resultado final después de los reintentos:
     *         - Resultado exitoso (código 0)
     *         - Error definitivo (código diferente de -600 y 71)
     *         - Error por timeout después de 60 segundos
     * @throws RuntimeException si el proceso es interrumpido
     */
    RecargaInternacional consultarRecargaConReintentos(RecargaInternacional recargaOriginal);
}
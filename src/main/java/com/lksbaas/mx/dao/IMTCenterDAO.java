/**
 * Interfaz Data Access Object (DAO) para operaciones con el servicio MTCenter.
 *
 * Define el contrato que deben implementar todas las clases que proporcionen
 * acceso a los servicios de MTCenter, asegurando consistencia en las operaciones
 * de autenticación, recargas, consultas y gestión de productos.
 *
 * Esta interfaz sigue el patrón de diseño DAO, separando la lógica de acceso a datos
 * de la lógica de negocio, facilitando testing y mantenimiento.
 */
package com.lksbaas.mx.dao;

import com.lksbaas.mx.model.*;

public interface IMTCenterDAO {

    /**
     * Realiza la autenticación con el servicio MTCenter y obtiene un token de acceso.
     * El token es necesario para todas las operaciones posteriores.
     *
     * @return MTCenterAuthResponse con la respuesta de autenticación que incluye:
     *         - token: Token JWT para autenticación en requests posteriores
     *         - tokenType: Tipo de token (generalmente "Bearer")
     *         - expiresIn: Tiempo de expiración en segundos
     *         - codigoRespuesta: Código numérico de resultado (0 = éxito)
     *         - mensajeRespuesta: Mensaje descriptivo del resultado
     * @throws RuntimeException si ocurre un error en el proceso de autenticación
     */
    MTCenterAuthResponse authenticate();

    /**
     * Realiza una recarga internacional a través del servicio MTCenter.
     * Requiere autenticación previa y token válido.
     *
     * @param recarga Objeto RecargaInternacional con los datos requeridos:
     *                - telefono: Número internacional destino
     *                - importe: Monto de la recarga
     *                - countryCode: Código de país ISO
     *                - mobileCountryCode: Código MCC
     *                - sku: Identificador del producto
     *                - noTransaccion: Número de transacción local
     *                - fechaHora: Timestamp de la transacción
     * @return RecargaInternacional con la respuesta que incluye:
     *         - codigoRespuesta: Código numérico de resultado
     *         - descripcionRespuesta: Mensaje descriptivo
     *         - noAutorizacion: Número de autorización
     *         - fechaHoraSolicitud: Timestamp de solicitud
     *         - fechaHoraRespuesta: Timestamp de respuesta
     * @throws RuntimeException si ocurre un error en el proceso de recarga
     */
    RecargaInternacional realizarRecarga(RecargaInternacional recarga);

    /**
     * Consulta el saldo disponible en la cuenta asociada a las credenciales.
     * Requiere autenticación previa y token válido.
     *
     * @return SaldoMTCenter con la información de saldo que incluye:
     *         - fechaHora: Timestamp de la consulta
     *         - codigoRespuesta: Código numérico de resultado
     *         - descripcionRespuesta: Mensaje descriptivo
     *         - saldo: Monto disponible en la cuenta
     * @throws RuntimeException si ocurre un error en la consulta de saldo
     */
    SaldoMTCenter consultarSaldo();

    /**
     * Consulta el catálogo completo de productos disponibles en MTCenter.
     * Incluye países, operadores y productos de recarga internacional.
     * Requiere autenticación previa y token válido.
     *
     * @return ProductosMTCenter con la estructura jerárquica que incluye:
     *         - codigoRespuesta: Código numérico de resultado
     *         - descripcionRespuesta: Mensaje descriptivo
     *         - productos: Lista de países → operadores → productos
     * @throws RuntimeException si ocurre un error en la consulta de productos
     */
    ProductosMTCenter consultarProductos();

    /**
     * Consulta el estado de una recarga previamente realizada.
     * Permite verificar el estado, autorización y detalles de una transacción.
     * Requiere autenticación previa y token válido.
     *
     * @param consulta Objeto ConsultaRecarga con los datos de búsqueda:
     *                 - telefono: Número consultado
     *                 - sku: Producto consultado
     *                 - noTransaccionRecarga: Número de transacción de recarga
     *                 - fechaHoraRecarga: Timestamp de la recarga
     *                 - noTransaccion: Número de transacción local
     *                 - fechaHora: Timestamp local
     * @return RecargaInternacional con la información de la recarga consultada:
     *         - codigoRespuesta: Código numérico de resultado
     *         - descripcionRespuesta: Mensaje descriptivo
     *         - noAutorizacion: Número de autorización
     *         - fechaHoraSolicitud: Timestamp de solicitud
     *         - fechaHoraRespuesta: Timestamp de respuesta
     * @throws RuntimeException si ocurre un error en la consulta de recarga
     */
    RecargaInternacional consultarRecarga(ConsultaRecarga consulta);
}
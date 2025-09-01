package com.lksbaas.mx.model;

import com.fasterxml.jackson.annotation.JsonInclude;

// Excluye campos con valores nulos en la serialización JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    private String error;
    private Integer errorCode;

    // ============ CONSTRUCTORES ============

    public ApiResponse() {}

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // ============ MÉTODOS DE FÁBRICA ESTÁTICOS ============

    /**
     * Crea una respuesta exitosa sin datos adjuntos
     * @param message Mensaje de éxito
     * @return Instancia de ApiResponse configurada como éxito
     */
    public static ApiResponse success(String message) {
        return new ApiResponse(true, message);
    }

    /**
     * Crea una respuesta exitosa con datos adjuntos
     * @param message Mensaje de éxito
     * @param data Objeto con información de retorno
     * @return Instancia de ApiResponse configurada como éxito con datos
     */
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    /**
     * Crea una respuesta de error con un mensaje descriptivo
     * @param message Descripción del error
     * @return Instancia de ApiResponse configurada como error
     */
    public static ApiResponse error(String message) {
        ApiResponse response = new ApiResponse(false, message);
        response.setError(message); // Establece el mensaje también en el campo error
        return response;
    }

    /**
     * Crea una respuesta de error con mensaje y código de error
     * @param message Descripción del error
     * @param errorCode Código numérico que categoriza el error
     * @return Instancia de ApiResponse configurada como error con código
     */
    public static ApiResponse error(String message, Integer errorCode) {
        ApiResponse response = new ApiResponse(false, message);
        response.setError(message);
        response.setErrorCode(errorCode);
        return response;
    }

    // ============ GETTERS Y SETTERS ============

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
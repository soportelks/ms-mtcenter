package com.lksbaas.mx.dto.servicios;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaldoResponse {

    @JsonProperty("fecha_hora")
    private String fechaHora;

    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;

    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;

    @JsonProperty("saldo")
    private Double saldo;

    // Constructor vacío
    public SaldoResponse() {}

    // Constructor con parámetros
    public SaldoResponse(String fechaHora, Integer codigoRespuesta,
                         String descripcionRespuesta, Double saldo) {
        this.fechaHora = fechaHora;
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
        this.saldo = saldo;
    }

    // Getters y Setters
    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(Integer codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
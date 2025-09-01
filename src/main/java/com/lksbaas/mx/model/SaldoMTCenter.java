package com.lksbaas.mx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaldoMTCenter {

    @JsonProperty("fecha_hora")
    private String fechaHora;

    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;

    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;

    private Double saldo;

    // ============ CONSTRUCTORES ============

    public SaldoMTCenter(){}

    public SaldoMTCenter(String fechaHora, Integer codigoRespuesta,
                         String descripcionRespuesta, Double saldo) {
        this.fechaHora = fechaHora;
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
        this.saldo = saldo;
    }

    // ============ GETTERS Y SETTERS ============

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
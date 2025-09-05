package com.lksbaas.mx.dto.servicios;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultaRecargaRequest {

    @JsonProperty("referencia1")
    private String referencia1;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("no_transaccion")
    private Integer noTransaccion;

    @JsonProperty("fecha_hora")
    private String fechaHora;

    // Constructor vacío
    public ConsultaRecargaRequest() {}

    // Constructor con parámetros
    public ConsultaRecargaRequest(String referencia1, String sku, Integer noTransaccion, String fechaHora) {
        this.referencia1 = referencia1;
        this.sku = sku;
        this.noTransaccion = noTransaccion;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public String getReferencia1() {
        return referencia1;
    }

    public void setReferencia1(String referencia1) {
        this.referencia1 = referencia1;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getNoTransaccion() {
        return noTransaccion;
    }

    public void setNoTransaccion(Integer noTransaccion) {
        this.noTransaccion = noTransaccion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}

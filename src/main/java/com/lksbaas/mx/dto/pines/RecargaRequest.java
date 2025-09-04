package com.lksbaas.mx.dto.pines;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecargaRequest {
    private String sku;

    @JsonProperty("no_transaccion")
    private Integer noTransaccion;

    @JsonProperty("fecha_hora")
    private String fechaHora;

    private String referencia1;
    private String referencia2;

    // Constructors
    public RecargaRequest() {}

    public RecargaRequest(String sku, Integer noTransaccion, String fechaHora, String referencia1, String referencia2) {
        this.sku = sku;
        this.noTransaccion = noTransaccion;
        this.fechaHora = fechaHora;
        this.referencia1 = referencia1;
        this.referencia2 = referencia2;
    }

    // Getters and Setters
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getNoTransaccion() { return noTransaccion; }
    public void setNoTransaccion(Integer noTransaccion) { this.noTransaccion = noTransaccion; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getReferencia1() { return referencia1; }
    public void setReferencia1(String referencia1) { this.referencia1 = referencia1; }

    public String getReferencia2() { return referencia2; }
    public void setReferencia2(String referencia2) { this.referencia2 = referencia2; }
}

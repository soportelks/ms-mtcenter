package com.lksbaas.mx.dto.tae;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecargaRequest {
    @JsonProperty("telefono")
    private String telefono;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("no_transaccion")
    private Integer noTransaccion;

    @JsonProperty("fecha_hora")
    private String fechaHora;

    @JsonProperty("cve_estado")
    private String cveEstado;

    // Constructor vacío
    public RecargaRequest() {}

    public RecargaRequest(String telefono, String sku, Integer noTransaccion, String fechaHora, String cveEstado) {
        this.telefono = telefono;
        this.sku = sku;
        this.noTransaccion = noTransaccion;
        this.fechaHora = fechaHora;
        this.cveEstado = cveEstado;
    }

    // Getters y Setters
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getNoTransaccion() { return noTransaccion; }
    public void setNoTransaccion(Integer noTransaccion) { this.noTransaccion = noTransaccion; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getCveEstado() { return cveEstado; }
    public void setCveEstado(String cveEstado) { this.cveEstado = cveEstado; }
}
package com.lksbaas.mx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaldoRequest {
    @JsonProperty("fecha_hora")
    private String fechaHora;

    // ============ CONSTRUCTORES ============

    public SaldoRequest() {}

    public SaldoRequest(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    // ============ GETTERS Y SETTERS ============

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
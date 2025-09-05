package com.lksbaas.mx.dto.servicios;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaldoRequest {

    @JsonProperty("fecha_hora")
    private String fechaHora;

    // Constructor vacío
    public SaldoRequest() {}

    // Constructor con parámetros
    public SaldoRequest(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
package com.lksbaas.mx.dto.pines;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaldoRequest {
    @JsonProperty("fecha_hora")
    private String fechaHora;

    // Constructors
    public SaldoRequest() {}

    public SaldoRequest(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Getters and Setters
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
}
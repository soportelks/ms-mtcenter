package com.lksbaas.mx.dto.tae;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaldoRequest {
    @JsonProperty("fecha_hora")
    private String fechaHora;

    public SaldoRequest() {}

    public SaldoRequest(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
}
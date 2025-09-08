package com.lksbaas.mx.dto.tae;

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

    public SaldoResponse() {}

    // Getters y Setters
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public Integer getCodigoRespuesta() { return codigoRespuesta; }
    public void setCodigoRespuesta(Integer codigoRespuesta) { this.codigoRespuesta = codigoRespuesta; }

    public String getDescripcionRespuesta() { return descripcionRespuesta; }
    public void setDescripcionRespuesta(String descripcionRespuesta) { this.descripcionRespuesta = descripcionRespuesta; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }
}
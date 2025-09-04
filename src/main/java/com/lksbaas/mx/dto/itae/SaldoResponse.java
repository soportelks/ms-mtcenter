package com.lksbaas.mx.dto.itae;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SaldoResponse {
    @JsonProperty("fecha_hora")
    private String fechaHora;
    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;
    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;
    private Float saldo;

    public SaldoResponse(){}

    public SaldoResponse(String fechaHora, Integer codigoRespuesta, String descripcionRespuesta, Float saldo) {
        this.fechaHora = fechaHora;
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
        this.saldo = saldo;
    }

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

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }
}

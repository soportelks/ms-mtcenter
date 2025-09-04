package com.lksbaas.mx.dto.pines;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultaRecargaResponse {
    private String referencia1;
    private String referencia2;
    private String referencia3;
    private String sku;

    @JsonProperty("no_transaccion")
    private Integer noTransaccion;

    @JsonProperty("fecha_hora")
    private String fechaHora;

    @JsonProperty("fecha_hora_respuesta")
    private String fechaHoraRespuesta;

    @JsonProperty("fecha_hora_solicitud")
    private String fechaHoraSolicitud;

    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;

    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;

    @JsonProperty("no_autorizacion")
    private String noAutorizacion;

    private String instruccion1;
    private String instruccion2;

    // Constructors
    public ConsultaRecargaResponse() {}

    // Getters and Setters
    public String getReferencia1() { return referencia1; }
    public void setReferencia1(String referencia1) { this.referencia1 = referencia1; }

    public String getReferencia2() { return referencia2; }
    public void setReferencia2(String referencia2) { this.referencia2 = referencia2; }

    public String getReferencia3() { return referencia3; }
    public void setReferencia3(String referencia3) { this.referencia3 = referencia3; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getNoTransaccion() { return noTransaccion; }
    public void setNoTransaccion(Integer noTransaccion) { this.noTransaccion = noTransaccion; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getFechaHoraRespuesta() { return fechaHoraRespuesta; }
    public void setFechaHoraRespuesta(String fechaHoraRespuesta) { this.fechaHoraRespuesta = fechaHoraRespuesta; }

    public String getFechaHoraSolicitud() { return fechaHoraSolicitud; }
    public void setFechaHoraSolicitud(String fechaHoraSolicitud) { this.fechaHoraSolicitud = fechaHoraSolicitud; }

    public Integer getCodigoRespuesta() { return codigoRespuesta; }
    public void setCodigoRespuesta(Integer codigoRespuesta) { this.codigoRespuesta = codigoRespuesta; }

    public String getDescripcionRespuesta() { return descripcionRespuesta; }
    public void setDescripcionRespuesta(String descripcionRespuesta) { this.descripcionRespuesta = descripcionRespuesta; }

    public String getNoAutorizacion() { return noAutorizacion; }
    public void setNoAutorizacion(String noAutorizacion) { this.noAutorizacion = noAutorizacion; }

    public String getInstruccion1() { return instruccion1; }
    public void setInstruccion1(String instruccion1) { this.instruccion1 = instruccion1; }

    public String getInstruccion2() { return instruccion2; }
    public void setInstruccion2(String instruccion2) { this.instruccion2 = instruccion2; }
}

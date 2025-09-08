package com.lksbaas.mx.dto.tae;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecargaResponse {
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

    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;

    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;

    @JsonProperty("no_autorizacion")
    private Integer noAutorizacion;

    @JsonProperty("instruccion1")
    private String instruccion1;

    @JsonProperty("instruccion2")
    private String instruccion2;

    @JsonProperty("fecha_hora_solicitud")
    private String fechaHoraSolicitud;

    @JsonProperty("fecha_hora_respuesta")
    private String fechaHoraRespuesta;

    // Constructor vacío
    public RecargaResponse() {}

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

    public Integer getCodigoRespuesta() { return codigoRespuesta; }
    public void setCodigoRespuesta(Integer codigoRespuesta) { this.codigoRespuesta = codigoRespuesta; }

    public String getDescripcionRespuesta() { return descripcionRespuesta; }
    public void setDescripcionRespuesta(String descripcionRespuesta) { this.descripcionRespuesta = descripcionRespuesta; }

    public Integer getNoAutorizacion() { return noAutorizacion; }
    public void setNoAutorizacion(Integer noAutorizacion) { this.noAutorizacion = noAutorizacion; }

    public String getInstruccion1() { return instruccion1; }
    public void setInstruccion1(String instruccion1) { this.instruccion1 = instruccion1; }

    public String getInstruccion2() { return instruccion2; }
    public void setInstruccion2(String instruccion2) { this.instruccion2 = instruccion2; }

    public String getFechaHoraSolicitud() { return fechaHoraSolicitud; }
    public void setFechaHoraSolicitud(String fechaHoraSolicitud) { this.fechaHoraSolicitud = fechaHoraSolicitud; }

    public String getFechaHoraRespuesta() { return fechaHoraRespuesta; }
    public void setFechaHoraRespuesta(String fechaHoraRespuesta) { this.fechaHoraRespuesta = fechaHoraRespuesta; }
}
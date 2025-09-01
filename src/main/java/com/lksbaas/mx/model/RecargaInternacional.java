package com.lksbaas.mx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecargaInternacional {

    // ============ CAMPOS DE ENTRADA (REQUEST) ============
    private String telefono;
    private Double importe;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("mobile_country_code")
    private String mobileCountryCode;

    private String sku;

    @JsonProperty("no_transaccion")
    private Integer noTransaccion;

    @JsonProperty("fecha_hora")
    private String fechaHora;

    // ============ CAMPOS DE RESPUESTA (RESPONSE) ============

    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;

    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;

    @JsonProperty("no_autorizacion")
    private Integer noAutorizacion;

    @JsonProperty("fecha_hora_solicitud")
    private String fechaHoraSolicitud;

    @JsonProperty("fecha_hora_respuesta")
    private String fechaHoraRespuesta;

    // ============ CONSTRUCTORES ============

    public RecargaInternacional(){}

    public RecargaInternacional(String telefono, String countryCode, Double importe,
                                String mobileCountryCode, String sku, Integer noTransaccion,
                                String fechaHora, Integer codigoRespuesta, String descripcionRespuesta,
                                Integer noAutorizacion, String fechaHoraSolicitud, String fechaHoraRespuesta) {
        this.telefono = telefono;
        this.countryCode = countryCode;
        this.importe = importe;
        this.mobileCountryCode = mobileCountryCode;
        this.sku = sku;
        this.noTransaccion = noTransaccion;
        this.fechaHora = fechaHora;
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
        this.noAutorizacion = noAutorizacion;
        this.fechaHoraSolicitud = fechaHoraSolicitud;
        this.fechaHoraRespuesta = fechaHoraRespuesta;
    }

    // ============ GETTERS Y SETTERS ============

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(String mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getNoTransaccion() {
        return noTransaccion;
    }

    public void setNoTransaccion(Integer noTransaccion) {
        this.noTransaccion = noTransaccion;
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

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public Integer getNoAutorizacion() {
        return noAutorizacion;
    }

    public void setNoAutorizacion(Integer noAutorizacion) {
        this.noAutorizacion = noAutorizacion;
    }

    public String getFechaHoraSolicitud() {
        return fechaHoraSolicitud;
    }

    public void setFechaHoraSolicitud(String fechaHoraSolicitud) {
        this.fechaHoraSolicitud = fechaHoraSolicitud;
    }

    public String getFechaHoraRespuesta() {
        return fechaHoraRespuesta;
    }

    public void setFechaHoraRespuesta(String fechaHoraRespuesta) {
        this.fechaHoraRespuesta = fechaHoraRespuesta;
    }
}
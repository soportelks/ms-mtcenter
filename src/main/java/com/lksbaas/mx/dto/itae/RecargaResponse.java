package com.lksbaas.mx.dto.itae;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecargaResponse {
    private String telefono;
    private Float importe;
    @JsonProperty("no_transaccion")
    private Integer noTransaccion;
    @JsonProperty("fecha_hora")
    private String fechaHora;
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
    private String sku;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("mobile_country_code")
    private String mobileCountryCode;

    public RecargaResponse(){}

    public RecargaResponse(String telefono, Float importe, Integer noTransaccion, String fechaHora, Integer codigoRespuesta, String descripcionRespuesta, Integer noAutorizacion, String fechaHoraSolicitud, String fechaHoraRespuesta, String sku, String countryCode, String mobileCountryCode) {
        this.telefono = telefono;
        this.importe = importe;
        this.noTransaccion = noTransaccion;
        this.fechaHora = fechaHora;
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
        this.noAutorizacion = noAutorizacion;
        this.fechaHoraSolicitud = fechaHoraSolicitud;
        this.fechaHoraRespuesta = fechaHoraRespuesta;
        this.sku = sku;
        this.countryCode = countryCode;
        this.mobileCountryCode = mobileCountryCode;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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
}

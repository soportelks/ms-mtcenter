package com.lksbaas.mx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecargaRequest {
    private String telefono;
    private Float importe;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("mobile_country_code")
    private String mobileCountryCode;
    private String sku;
    @JsonProperty("no_transaccion")
    private Integer noTransaccion;
    @JsonProperty("fecha_hora")
    private String fechaHora;

    public RecargaRequest(){}

    public RecargaRequest(String telefono, Float importe, String countryCode, String sku, String mobileCountryCode, Integer noTransaccion, String fechaHora) {
        this.telefono = telefono;
        this.importe = importe;
        this.countryCode = countryCode;
        this.sku = sku;
        this.mobileCountryCode = mobileCountryCode;
        this.noTransaccion = noTransaccion;
        this.fechaHora = fechaHora;
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
}
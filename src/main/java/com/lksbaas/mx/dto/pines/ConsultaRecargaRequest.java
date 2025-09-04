package com.lksbaas.mx.dto.pines;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultaRecargaRequest {
    private String referencia1;
    private String referencia2;
    private String sku;

    @JsonProperty("no_transaccion_pin")
    private Integer noTransaccionPin;

    @JsonProperty("fecha_hora_pin")
    private String fechaHoraPin;

    @JsonProperty("no_transaccion")
    private Integer noTransaccion;

    @JsonProperty("fecha_hora")
    private String fechaHora;

    // Constructors
    public ConsultaRecargaRequest() {}

    public ConsultaRecargaRequest(String referencia1, String referencia2, String sku, Integer noTransaccionPin,
                                  String fechaHoraPin, Integer noTransaccion, String fechaHora) {
        this.referencia1 = referencia1;
        this.referencia2 = referencia2;
        this.sku = sku;
        this.noTransaccionPin = noTransaccionPin;
        this.fechaHoraPin = fechaHoraPin;
        this.noTransaccion = noTransaccion;
        this.fechaHora = fechaHora;
    }

    // Getters and Setters
    public String getReferencia1() { return referencia1; }
    public void setReferencia1(String referencia1) { this.referencia1 = referencia1; }

    public String getReferencia2() { return referencia2; }
    public void setReferencia2(String referencia2) { this.referencia2 = referencia2; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getNoTransaccionPin() { return noTransaccionPin; }
    public void setNoTransaccionPin(Integer noTransaccionPin) { this.noTransaccionPin = noTransaccionPin; }

    public String getFechaHoraPin() { return fechaHoraPin; }
    public void setFechaHoraPin(String fechaHoraPin) { this.fechaHoraPin = fechaHoraPin; }

    public Integer getNoTransaccion() { return noTransaccion; }
    public void setNoTransaccion(Integer noTransaccion) { this.noTransaccion = noTransaccion; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
}


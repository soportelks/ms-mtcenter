package com.lksbaas.mx.dto.tae;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultaRecargaRequest {
    @JsonProperty("telefono")
    private String telefono;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("no_transaccion_recarga")
    private Integer noTransaccionRecarga;

    @JsonProperty("fecha_hora_recarga")
    private String fechaHoraRecarga;

    @JsonProperty("no_transaccion")
    private Integer noTransaccion;

    @JsonProperty("fecha_hora")
    private String fechaHora;

    public ConsultaRecargaRequest() {}

    public ConsultaRecargaRequest(String telefono, String sku, Integer noTransaccionRecarga,
                                  String fechaHoraRecarga, Integer noTransaccion, String fechaHora) {
        this.telefono = telefono;
        this.sku = sku;
        this.noTransaccionRecarga = noTransaccionRecarga;
        this.fechaHoraRecarga = fechaHoraRecarga;
        this.noTransaccion = noTransaccion;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getNoTransaccionRecarga() { return noTransaccionRecarga; }
    public void setNoTransaccionRecarga(Integer noTransaccionRecarga) { this.noTransaccionRecarga = noTransaccionRecarga; }

    public String getFechaHoraRecarga() { return fechaHoraRecarga; }
    public void setFechaHoraRecarga(String fechaHoraRecarga) { this.fechaHoraRecarga = fechaHoraRecarga; }

    public Integer getNoTransaccion() { return noTransaccion; }
    public void setNoTransaccion(Integer noTransaccion) { this.noTransaccion = noTransaccion; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
}

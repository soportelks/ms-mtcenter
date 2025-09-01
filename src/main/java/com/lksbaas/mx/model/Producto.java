package com.lksbaas.mx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Producto {
    private String label;

    @JsonProperty("clave_producto")
    private String claveProducto;

    @JsonProperty("desc_ticket")
    private String descTicket;

    private Double costo;

    // ============ CONSTRUCTORES ============

    public Producto() {}

    public Producto(String label, String claveProducto, String descTicket, Double costo) {
        this.label = label;
        this.claveProducto = claveProducto;
        this.descTicket = descTicket;
        this.costo = costo;
    }

    // ============ GETTERS Y SETTERS ============

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public void setClaveProducto(String claveProducto) {
        this.claveProducto = claveProducto;
    }

    public String getDescTicket() {
        return descTicket;
    }

    public void setDescTicket(String descTicket) {
        this.descTicket = descTicket;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }
}
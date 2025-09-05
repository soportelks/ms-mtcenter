package com.lksbaas.mx.dto.prepago;

public class Producto {
    private String Servicio;
    private String ClaveProducto;
    private String Producto;
    private Double Importe;
    private Double MaxImporte;
    private Double Costo;
    private String TipoReferencia;
    private String Regex;
    private Integer LongitudMaxima;

    public Producto() {}

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String servicio) {
        Servicio = servicio;
    }

    public String getClaveProducto() {
        return ClaveProducto;
    }

    public void setClaveProducto(String claveProducto) {
        ClaveProducto = claveProducto;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public Double getImporte() {
        return Importe;
    }

    public void setImporte(Double importe) {
        Importe = importe;
    }

    public Double getMaxImporte() {
        return MaxImporte;
    }

    public void setMaxImporte(Double maxImporte) {
        MaxImporte = maxImporte;
    }

    public Double getCosto() {
        return Costo;
    }

    public void setCosto(Double costo) {
        Costo = costo;
    }

    public String getTipoReferencia() {
        return TipoReferencia;
    }

    public void setTipoReferencia(String tipoReferencia) {
        TipoReferencia = tipoReferencia;
    }

    public String getRegex() {
        return Regex;
    }

    public void setRegex(String regex) {
        Regex = regex;
    }

    public Integer getLongitudMaxima() {
        return LongitudMaxima;
    }

    public void setLongitudMaxima(Integer longitudMaxima) {
        LongitudMaxima = longitudMaxima;
    }
}
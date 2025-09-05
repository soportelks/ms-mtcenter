package com.lksbaas.mx.dto.servicios;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProductosResponse {

    @JsonProperty("productos")
    private List<Producto> productos;

    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;

    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;

    // Constructor vacío
    public ProductosResponse() {}

    // Constructor con parámetros
    public ProductosResponse(List<Producto> productos, Integer codigoRespuesta, String descripcionRespuesta) {
        this.productos = productos;
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
    }

    // Getters y Setters
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
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

    // Clase interna para Producto
    public static class Producto {

        @JsonProperty("Servicio")
        private String servicio;

        @JsonProperty("ClaveProducto")
        private String claveProducto;

        @JsonProperty("Producto")
        private String producto;

        @JsonProperty("Importe")
        private Double importe;

        @JsonProperty("MaxImporte")
        private Double maxImporte;

        @JsonProperty("Costo")
        private Double costo;

        @JsonProperty("TipoReferencia")
        private String tipoReferencia;

        @JsonProperty("Regex")
        private String regex;

        @JsonProperty("LongitudMaxima")
        private Integer longitudMaxima;

        // Constructor vacío
        public Producto() {}

        // Getters y Setters
        public String getServicio() {
            return servicio;
        }

        public void setServicio(String servicio) {
            this.servicio = servicio;
        }

        public String getClaveProducto() {
            return claveProducto;
        }

        public void setClaveProducto(String claveProducto) {
            this.claveProducto = claveProducto;
        }

        public String getProducto() {
            return producto;
        }

        public void setProducto(String producto) {
            this.producto = producto;
        }

        public Double getImporte() {
            return importe;
        }

        public void setImporte(Double importe) {
            this.importe = importe;
        }

        public Double getMaxImporte() {
            return maxImporte;
        }

        public void setMaxImporte(Double maxImporte) {
            this.maxImporte = maxImporte;
        }

        public Double getCosto() {
            return costo;
        }

        public void setCosto(Double costo) {
            this.costo = costo;
        }

        public String getTipoReferencia() {
            return tipoReferencia;
        }

        public void setTipoReferencia(String tipoReferencia) {
            this.tipoReferencia = tipoReferencia;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public Integer getLongitudMaxima() {
            return longitudMaxima;
        }

        public void setLongitudMaxima(Integer longitudMaxima) {
            this.longitudMaxima = longitudMaxima;
        }
    }
}
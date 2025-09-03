package com.lksbaas.mx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductosResponse {
    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;
    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;
    private List<Pais> productos;

    @Data
    public static class Pais {
        private String pais;
        private String isoCode;
        private String lada;
        private List<Operador> operadores;
    }

    @Data
    public static class Operador {
        private String operador;
        private Integer producto;
        private List<Producto> productos;
    }

    @Data
    public static class Producto {
        private String label;
        @JsonProperty("clave_producto")
        private String claveProducto;
        @JsonProperty("desc_ticket")
        private String descTicket;
        private Float costo;
    }

    public ProductosResponse(){}

    public ProductosResponse(Integer codigoRespuesta, String descripcionRespuesta, List<Pais> productos) {
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
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

    public List<Pais> getProductos() {
        return productos;
    }

    public void setProductos(List<Pais> productos) {
        this.productos = productos;
    }
}

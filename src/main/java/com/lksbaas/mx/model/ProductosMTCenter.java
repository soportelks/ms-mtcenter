package com.lksbaas.mx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProductosMTCenter {
    @JsonProperty("codigo_respuesta")
    private Integer codigoRespuesta;

    @JsonProperty("descripcion_respuesta")
    private String descripcionRespuesta;

    /**
     * Lista jerárquica de países con sus operadores y productos
     * Estructura: País → Operador → Producto
     */
    private List<Pais> productos;

    // ============ CONSTRUCTORES ============

    public ProductosMTCenter() {}

    public ProductosMTCenter(Integer codigoRespuesta, String descripcionRespuesta, List<Pais> productos) {
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
        this.productos = productos;
    }

    // ============ GETTERS Y SETTERS ============

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
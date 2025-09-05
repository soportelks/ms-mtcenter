package com.lksbaas.mx.dto.prepago;

import java.util.List;

public class ProductosResponse {
    private List<Producto> productos;
    private Integer codigo_respuesta;
    private String descripcion_respuesta;

    public ProductosResponse() {}

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Integer getCodigo_respuesta() {
        return codigo_respuesta;
    }

    public void setCodigo_respuesta(Integer codigo_respuesta) {
        this.codigo_respuesta = codigo_respuesta;
    }

    public String getDescripcion_respuesta() {
        return descripcion_respuesta;
    }

    public void setDescripcion_respuesta(String descripcion_respuesta) {
        this.descripcion_respuesta = descripcion_respuesta;
    }
}
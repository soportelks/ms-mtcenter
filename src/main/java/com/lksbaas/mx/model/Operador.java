package com.lksbaas.mx.model;

import java.util.List;

public class Operador {
    private String operador;
    private Integer producto;

    /**
     * Lista de productos disponibles para este operador
     */
    private List<Producto> productos;

    // ============ CONSTRUCTORES ============

    public Operador() {}

    public Operador(String operador, Integer producto, List<Producto> productos) {
        this.operador = operador;
        this.producto = producto;
        this.productos = productos;
    }

    // ============ GETTERS Y SETTERS ============

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
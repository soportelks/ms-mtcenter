package com.lksbaas.mx.dto.prepago;

public class ConsultaPrepagoResponse {
    private String referencia1;
    private String sku;
    private Integer no_transaccion;
    private String fecha_hora;
    private Integer codigo_respuesta;
    private String descripcion_respuesta;
    private Integer no_autorizacion;
    private String instruccion1;
    private String instruccion2;
    private String fecha_hora_solicitud;
    private String fecha_hora_respuesta;

    public ConsultaPrepagoResponse() {}

    public String getReferencia1() {
        return referencia1;
    }

    public void setReferencia1(String referencia1) {
        this.referencia1 = referencia1;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getNo_transaccion() {
        return no_transaccion;
    }

    public void setNo_transaccion(Integer no_transaccion) {
        this.no_transaccion = no_transaccion;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
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

    public Integer getNo_autorizacion() {
        return no_autorizacion;
    }

    public void setNo_autorizacion(Integer no_autorizacion) {
        this.no_autorizacion = no_autorizacion;
    }

    public String getInstruccion1() {
        return instruccion1;
    }

    public void setInstruccion1(String instruccion1) {
        this.instruccion1 = instruccion1;
    }

    public String getInstruccion2() {
        return instruccion2;
    }

    public void setInstruccion2(String instruccion2) {
        this.instruccion2 = instruccion2;
    }

    public String getFecha_hora_solicitud() {
        return fecha_hora_solicitud;
    }

    public void setFecha_hora_solicitud(String fecha_hora_solicitud) {
        this.fecha_hora_solicitud = fecha_hora_solicitud;
    }

    public String getFecha_hora_respuesta() {
        return fecha_hora_respuesta;
    }

    public void setFecha_hora_respuesta(String fecha_hora_respuesta) {
        this.fecha_hora_respuesta = fecha_hora_respuesta;
    }
}
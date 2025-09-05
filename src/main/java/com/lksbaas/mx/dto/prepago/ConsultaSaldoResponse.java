package com.lksbaas.mx.dto.prepago;

public class ConsultaSaldoResponse {
    private String fecha_hora;
    private Integer codigo_respuesta;
    private String descripcion_respuesta;
    private Double saldo;

    public ConsultaSaldoResponse() {}

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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
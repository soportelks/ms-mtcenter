package com.lksbaas.mx.dto.prepago;

public class RecargaPrepagoRequest {
    private String referencia1;
    private String sku;
    private Integer no_transaccion;
    private String fecha_hora;

    public RecargaPrepagoRequest() {}

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
}
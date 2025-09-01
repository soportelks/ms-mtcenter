package com.lksbaas.mx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Pais {
    private String pais;
    @JsonProperty("isoCode")
    private String isoCode;
    private String lada;

    /**
     * Lista de operadores de telefonía móvil disponibles en el país
     */
    private List<Operador> operadores;

    // ============ CONSTRUCTORES ============

    public Pais() {}

    public Pais(String pais, String isoCode, String lada, List<Operador> operadores) {
        this.pais = pais;
        this.isoCode = isoCode;
        this.lada = lada;
        this.operadores = operadores;
    }

    // ============ GETTERS Y SETTERS ============

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getLada() {
        return lada;
    }

    public void setLada(String lada) {
        this.lada = lada;
    }

    public List<Operador> getOperadores() {
        return operadores;
    }

    public void setOperadores(List<Operador> operadores) {
        this.operadores = operadores;
    }
}
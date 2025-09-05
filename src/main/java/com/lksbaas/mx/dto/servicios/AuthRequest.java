package com.lksbaas.mx.dto.servicios;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequest {

    @JsonProperty("cadena")
    private Integer cadena;

    @JsonProperty("establecimiento")
    private Integer establecimiento;

    @JsonProperty("terminal")
    private Integer terminal;

    @JsonProperty("cajero")
    private Integer cajero;

    @JsonProperty("clave")
    private String clave;

    // Constructor vacío
    public AuthRequest() {}

    // Constructor con parámetros
    public AuthRequest(Integer cadena, Integer establecimiento, Integer terminal, Integer cajero, String clave) {
        this.cadena = cadena;
        this.establecimiento = establecimiento;
        this.terminal = terminal;
        this.cajero = cajero;
        this.clave = clave;
    }

    // Getters y Setters
    public Integer getCadena() {
        return cadena;
    }

    public void setCadena(Integer cadena) {
        this.cadena = cadena;
    }

    public Integer getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Integer establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Integer getCajero() {
        return cajero;
    }

    public void setCajero(Integer cajero) {
        this.cajero = cajero;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
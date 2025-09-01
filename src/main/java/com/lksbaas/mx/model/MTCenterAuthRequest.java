
package com.lksbaas.mx.model;

public class MTCenterAuthRequest {
    private Integer cadena;
    private Integer establecimiento;
    private Integer terminal;
    private Integer cajero;
    private String clave;

    // ============ CONSTRUCTORES ============

    public MTCenterAuthRequest(){}

    public MTCenterAuthRequest(Integer cadena, Integer establecimiento, Integer terminal,
                               Integer cajero, String clave) {
        this.cadena = cadena;
        this.establecimiento = establecimiento;
        this.terminal = terminal;
        this.cajero = cajero;
        this.clave = clave;
    }

    // ============ GETTERS Y SETTERS ============

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
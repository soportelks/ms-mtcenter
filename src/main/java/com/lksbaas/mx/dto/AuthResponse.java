package com.lksbaas.mx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {
    private String token;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("codigoRespuesta")
    private Integer codigoRespuesta;
    @JsonProperty("mensajeRespuesta")
    private String mensajeRespuesta;

    public AuthResponse(){}

    public AuthResponse(String token, String tokenType, String expiresIn, Integer codigoRespuesta, String mensajeRespuesta) {
        this.token = token;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.codigoRespuesta = codigoRespuesta;
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(Integer codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }
}

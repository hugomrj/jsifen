package com.jsifen.presentation.rest.consulta.ruc.dto.response;


public class ConsultarRucResponse {

    private String codigoRespuesta;
    private String mensajeRespuesta;
    private Integer statusCode;
    private String error;
    private DatosRuc datosRUC;

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public DatosRuc getDatosRUC() {
        return datosRUC;
    }

    public void setDatosRUC(DatosRuc datosRUC) {
        this.datosRUC = datosRUC;
    }
}

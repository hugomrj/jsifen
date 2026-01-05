package com.jsifen.presentation.rest.factura.dto;

public class FacturaFirmadaResponse {

    private String cdc;
    private String numeroFactura;
    private Integer tipoDocumento;
    private String fechaEmision;
    private String xml;

    public FacturaFirmadaResponse(
            String cdc,
            String numeroFactura,
            Integer tipoDocumento,
            String fechaEmision,
            String xml
    ) {
        this.cdc = cdc;
        this.numeroFactura = numeroFactura;
        this.tipoDocumento = tipoDocumento;
        this.fechaEmision = fechaEmision;
        this.xml = xml;
    }

    public String getCdc() {
        return cdc;
    }

    public void setCdc(String cdc) {
        this.cdc = cdc;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}

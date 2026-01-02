package com.jsifen.presentation.rest.factura.dto;

public class FacturaFirmadaResponse {

    private String cdc;
    private String xml;

    public FacturaFirmadaResponse() {}

    public FacturaFirmadaResponse(String cdc, String xml) {
        this.cdc = cdc;
        this.xml = xml;
    }

    public String getCdc() {
        return cdc;
    }

    public String getXml() {
        return xml;
    }
}

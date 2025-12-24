package com.jsifen.infrastructure.sifen.xml.buiilder;

import com.jsifen.infrastructure.sifen.xml.gen.CdcGenerator;
import com.jsifen.infrastructure.sifen.xml.gen.DeComplemento;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.json.JsonObject;


@RequestScoped
public class SifenFacturaXmlGenerator {
/*
    @Inject
    SifenPropierties sifenPropierties;
*/
    @Inject
DeComplemento complegen;

    @Inject
    Provider<DeXmlBuilder> deXmlBuilderProvider;

    private String jsonAll;

    public String generar(JsonObject facturaJson) throws Exception {
        // convertir a String
        String json = facturaJson.toString().trim();

        String cdc = CdcGenerator.obtenerCDC(json);

        String jsonCom = complegen.getJsonCom(json, cdc);

        jsonAll = jsonCom.substring(0, jsonCom.length() - 1) + ", "
                + json.substring(1, json.length());

        String xmlString = deXmlBuilderProvider.get().generateXml(jsonAll, cdc);
        //DeXmlBuilder builder = deXmlBuilderProvider.get();

        return xmlString;
    }
}

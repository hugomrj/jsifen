package py.com.jsifen.domain.de.gen;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.json.JsonObject;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;
import py.com.jsifen.infrastructure.util.sifen.xml.DeXmlBuilder;

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

        String jsonCom = complegen.getJsonCom(json);

        //DeXmlBuilder factura = new DeXmlBuilder();
        //deXmlBuilder.setComplemento(complegen);

        jsonAll = jsonCom.substring(0, jsonCom.length() - 1) + ", "
                + json.substring(1, json.length());

        String xmlString = deXmlBuilderProvider.get().generateXml(jsonAll);
        //DeXmlBuilder builder = deXmlBuilderProvider.get();


        // Imprimir en consola
        System.out.println("CDC: " + cdc);
        //System.out.println("Complemento: " + jsonCom);
        System.out.println("All: " + jsonAll);



        return xmlString;
    }
}

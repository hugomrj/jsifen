package py.com.jsifen.domain.de.gen;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;
import py.com.jsifen.infrastructure.util.sifen.xml.DeXmlBuilder;

@ApplicationScoped
public class SifenFacturaXmlGenerator {

    @Inject
    SifenPropierties sifenPropierties;

    public String generar(JsonObject facturaJson) throws Exception {
        // convertir a String
        String json = facturaJson.toString().trim();

        CdcGenerator cdcgem = new CdcGenerator();
        String cdc = cdcgem.obtenerCDC(json);

        DeComplemento complegen = new DeComplemento();
        String jsonCom = complegen.getJsonCom(json);

        DeXmlBuilder factura = new DeXmlBuilder();
        factura.setComplemento(complegen);


        // Imprimir en consola
        System.out.println("CDC: " + cdc);
        System.out.println("Complemento: " + jsonCom);


        /*

        complemento.obtenerCDC(json);
    */



        String xmlString = "generar xml";
        System.out.println(xmlString);
        return xmlString;
    }
}

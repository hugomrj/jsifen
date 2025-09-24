package py.com.jsifen.infrastructure.sifen.xml;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonObject;

@ApplicationScoped
public class SifenFacturaXmlGenerator {

    public String generar(JsonObject facturaJson) throws Exception {
        // Aquí tu lógica de conversión JSON→XML específica de SIFEN
        String xmlString = "generar xml";
        System.out.println(xmlString);
        return xmlString;
    }
}

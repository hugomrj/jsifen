package py.com.jsifen.application.usecase.factura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import py.com.jsifen.domain.repository.FacturaRepository;
import py.com.jsifen.domain.de.gen.SifenFacturaXmlGenerator;


@ApplicationScoped
public class RecibirFacturaUseCase {

    @Inject
    FacturaRepository facturaRepository;

    @Inject
    SifenFacturaXmlGenerator xmlGenerator;

    public JsonObject execute(JsonObject facturaInput) throws Exception {


        System.out.println(" JsonObject execute(JsonObject facturaJson)");
        // 1. Convertir JSON a XML
        String xmlOuput = xmlGenerator.generar(facturaInput);
        System.out.println(xmlOuput);

/*


        // 2. Firmar
        String xmlFirmado = xmlSigner.sign(xml);



        // 3. Enviar al servicio SOAP
        String respuestaXml = soapClient.enviar(xmlFirmado);

        // 4. Convertir respuesta a JSON
        JsonObject respuestaJson = SoapUtil.convertXmlToJson(respuestaXml);

        // 5. Guardar si corresponde
        facturaRepository.guardar(respuestaJson);

         */


        JsonObject respuestaJson = facturaRepository.enviarFactura(facturaInput);

        return respuestaJson;

    }


}
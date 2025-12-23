package py.com.jsifen.application.usecase.factura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.w3c.dom.Node;
import py.com.jsifen.domain.repository.FacturaRepository;
import py.com.jsifen.domain.de.gen.SifenFacturaXmlGenerator;
import py.com.jsifen.infrastructure.util.sifen.xml.FileXML;
import py.com.jsifen.infrastructure.util.sifen.xml.QrNodeBuilder;
import py.com.jsifen.infrastructure.soap.signer.SifenXmlSigner;


@ApplicationScoped
public class RecibirFacturaUseCase {

    @Inject
    FacturaRepository facturaRepository;

    @Inject
    SifenFacturaXmlGenerator xmlGenerator;

    @Inject
    SifenXmlSigner xmlSigner;

    @Inject
    QrNodeBuilder qrNodeBuilder;

    public JsonObject execute(JsonObject facturaInput) throws Exception {

        // 1. Convertir JSON a XML
        String xmlGenerado = xmlGenerator.generar(facturaInput);

        // 2. Firmar (devuelve DOM firmado)
        Node nodoFirmado = xmlSigner.signXml(xmlGenerado);

        // 3. Agregar QR al DOM firmado
        Node nodoConQR = qrNodeBuilder.addQrNode(nodoFirmado);

        // 4. Pasar el DOM final a String
        String xmlFinal = FileXML.xmlToString(nodoConQR);

        // 5. Enviar y obtener respuesta
        JsonObject respuestaJson = facturaRepository.enviarFactura(xmlFinal);

        //JsonObject respuestaJson = null;
        return respuestaJson;

    }


}
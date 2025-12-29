package com.jsifen.application.usecase.factura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.w3c.dom.Node;
import com.jsifen.domain.repository.FacturaRepository;
import com.jsifen.infrastructure.sifen.xml.buiilder.SifenFacturaXmlGenerator;
import com.jsifen.infrastructure.util.xml.parser.FileXML;
import com.jsifen.infrastructure.sifen.xml.buiilder.QrNodeBuilder;
import com.jsifen.infrastructure.soap.signer.SifenXmlSigner;


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

        System.out.println(">> execute() - inicio");

        // 1. Convertir JSON a XML
        System.out.println(">> Paso 1: generar XML");
        String xmlGenerado = xmlGenerator.generar(facturaInput);

        // 2. Firmar
        System.out.println(">> Paso 2: firmar XML");
        Node nodoFirmado = xmlSigner.signXml(xmlGenerado);

        // 3. Agregar QR
        System.out.println(">> Paso 3: agregar QR");
        Node nodoConQR = qrNodeBuilder.addQrNode(nodoFirmado);

        // 4. Convertir a String
        System.out.println(">> Paso 4: convertir XML a String");
        String xmlFinal = FileXML.xmlToString(nodoConQR);

        // 5. Enviar
        System.out.println(">> Paso 5: enviar factura");
        JsonObject respuestaJson = facturaRepository.enviarFactura(xmlFinal);

        System.out.println(">> execute() - fin");

        return respuestaJson;
    }

}
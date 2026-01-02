package com.jsifen.application.usecase.factura;


import com.jsifen.infrastructure.sifen.xml.buiilder.QrNodeBuilder;
import com.jsifen.infrastructure.sifen.xml.buiilder.SifenFacturaXmlGenerator;
import com.jsifen.infrastructure.soap.signer.SifenXmlSigner;
import com.jsifen.infrastructure.util.xml.XmlUtils;
import com.jsifen.infrastructure.util.xml.parser.FileXML;
import com.jsifen.presentation.rest.factura.dto.FacturaFirmadaResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.w3c.dom.Node;


@ApplicationScoped
public class GenerarXmlFacturaUseCase {

    @Inject
    SifenFacturaXmlGenerator xmlGenerator;

    @Inject
    SifenXmlSigner xmlSigner;

    @Inject
    QrNodeBuilder qrNodeBuilder;

    public FacturaFirmadaResponse ejecutar(JsonObject facturaInput) throws Exception {

        System.out.println("==> [XML] Inicio generación de XML");

        // 1. Generar XML
        String xmlGenerado = xmlGenerator.generar(facturaInput);

        // 2. Firmar XML
        Node nodoFirmado = xmlSigner.signXml(xmlGenerado);

        // 3. Agregar QR
        Node nodoConQR = qrNodeBuilder.addQrNode(nodoFirmado);

        // 4. Obtener CDC desde el XML
        String cdc = XmlUtils.obtenerCdcDesdeXml(nodoConQR);

        // 5. Convertir XML a String
        String xmlFinal = FileXML.xmlToString(nodoConQR);

        return new FacturaFirmadaResponse(cdc, xmlFinal);
    }


}

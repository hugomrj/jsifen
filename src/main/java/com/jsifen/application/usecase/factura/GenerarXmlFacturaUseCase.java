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



        //  Generar XML
        String xmlGenerado = xmlGenerator.generar(facturaInput);

        //  Firmar XML
        Node nodoFirmado = xmlSigner.signXml(xmlGenerado);

        //  Agregar QR
        Node nodo = qrNodeBuilder.addQrNode(nodoFirmado);


        String cdc = XmlUtils.obtenerCdcDesdeXml(nodo);
        String numeroFactura = XmlUtils.obtenerNumeroFacturaDesdeXml(nodo);
        Integer tipoDocumento = XmlUtils.obtenerTipoDocumentoDesdeXml(nodo);
        String fechaEmision = XmlUtils.obtenerFechaEmisionDesdeXml(nodo);

        // XML final
        String xmlFinal = FileXML.xmlToString(nodo);

        return new FacturaFirmadaResponse(
                cdc,
                numeroFactura,
                tipoDocumento,
                fechaEmision,
                xmlFinal
        );
    }



}

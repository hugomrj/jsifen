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

        System.out.println("ENTRA A ejecutar()");

        // 1. Generar XML
        System.out.println("PASO 1: antes de generar XML");
        String xmlGenerado = xmlGenerator.generar(facturaInput);
        System.out.println("PASO 1 OK");

        // 2. Firmar XML
        System.out.println("PASO 2: antes de firmar XML");
        Node nodoFirmado = xmlSigner.signXml(xmlGenerado);
        System.out.println("PASO 2 OK");

        // 3. Agregar QR
        System.out.println("PASO 3: antes de agregar QR");
        Node nodo = qrNodeBuilder.addQrNode(nodoFirmado);
        System.out.println("PASO 3 OK");


        // CDC
        String cdc = XmlUtils.obtenerCdcDesdeXml(nodo);
        if (cdc == null || cdc.isBlank()) {
            System.out.println("❌ CDC vacío");
        } else {
            System.out.println("✅ CDC = [" + cdc + "]");
        }


        String numeroFactura = XmlUtils.obtenerNumeroFacturaDesdeXml(nodo);
        if (numeroFactura == null || numeroFactura.isBlank()) {
            System.out.println("❌ Número de factura vacío");
        } else {
            System.out.println("✅ Número factura = [" + numeroFactura + "]");
        }


        Integer tipoDocumento = XmlUtils.obtenerTipoDocumentoDesdeXml(nodo);
        if (tipoDocumento == null) {
            System.out.println("❌ Tipo de documento vacío");
        } else {
            System.out.println("✅ Tipo documento = [" + tipoDocumento + "]");
        }


        String fechaEmision = XmlUtils.obtenerFechaEmisionDesdeXml(nodo);
        if (fechaEmision == null || fechaEmision.isBlank()) {
            System.out.println("❌ Fecha de emisión vacía");
        } else {
            System.out.println("✅ Fecha emisión = [" + fechaEmision + "]");
        }

        // XML final
        System.out.println("PASO 5: antes de convertir XML a String");
         String xmlFinal = FileXML.xmlToString(nodo);
        System.out.println("PASO 5 OK");

        System.out.println("SALE de ejecutar()");

        return new FacturaFirmadaResponse(
                cdc,
                numeroFactura,
                tipoDocumento,
                fechaEmision,
                xmlFinal
        );
    }



}

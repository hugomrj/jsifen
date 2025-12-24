package com.jsifen.infrastructure.soap.request;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.w3c.dom.Node;
import com.jsifen.infrastructure.soap.builder.EventoSoapBuilder;
import com.jsifen.infrastructure.soap.signer.EventoXmlSigner;
import com.jsifen.infrastructure.util.time.ClienteNTP;
import com.jsifen.infrastructure.soap.message.SoapIdGenerator;
import com.jsifen.infrastructure.util.xml.parser.FileXML;

@ApplicationScoped
public class EventoCancelarSoapRequest {

    @Inject
    EventoSoapBuilder eventoSoapBuilder;

    @Inject
    EventoXmlSigner eventoXmlSigner;

    public String createCancelarXml(String cdc, String motivo) {

        String xmlId = SoapIdGenerator.generateId();
        String fechaFirma = ClienteNTP.getTimeFormat();

        String xmlBase = """
        <gGroupGesEve xmlns="http://ekuatia.set.gov.py/sifen/xsd"
                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                      xsi:schemaLocation="http://ekuatia.set.gov.py/sifen/xsd siRecepEvento_v150.xsd">
            <rGesEve xsi:schemaLocation="https://ekuatia.set.gov.py/sifen/xsd siRecepEvento_v150.xsd">
                <rEve Id="%s">
                    <dFecFirma>%s</dFecFirma>
                    <dVerFor>150</dVerFor>
                    <gGroupTiEvt>
                        <rGeVeCan>
                            <Id>%s</Id>
                            <mOtEve>%s</mOtEve>
                        </rGeVeCan>
                    </gGroupTiEvt>
                </rEve>
            </rGesEve>
        </gGroupGesEve>
        """.formatted(xmlId, fechaFirma, cdc, motivo);

        // Firmar XML
        Node firmado = eventoXmlSigner.firmar(xmlBase);

        String xmlFirmado = FileXML.xmlToString(firmado);

        return eventoSoapBuilder.build(xmlId, xmlFirmado);
    }
}
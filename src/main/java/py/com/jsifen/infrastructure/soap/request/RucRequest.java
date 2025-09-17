package py.com.jsifen.infrastructure.soap.request;

import jakarta.enterprise.context.ApplicationScoped;
import py.com.jsifen.infrastructure.soap.util.SoapUtil;

@ApplicationScoped
public class RucRequest {

    public String createQueryXml(String ruc) {

        String xmlId = SoapUtil.xmlId() ;

        String xmlRequest = """
            <?xml version="1.0" encoding="UTF-8"?>
            <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope"
                           xmlns:xsd="http://ekuatia.set.gov.py/sifen/xsd">
                <soap:Header/>
                <soap:Body>
                    <xsd:rEnviConsRUC xmlns="http://ekuatia.set.gov.py/sifen/xsd"
                                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                      xsi:schemaLocation="http://ekuatia.set.gov.py/sifen/xsd/ siConsRUC_v150.xsd">
                        <xsd:dId>%s</xsd:dId>
                        <xsd:dRUCCons>%s</xsd:dRUCCons>
                    </xsd:rEnviConsRUC>
                </soap:Body>
            </soap:Envelope>
            """.formatted(xmlId, ruc);

        return xmlRequest;
    }

}
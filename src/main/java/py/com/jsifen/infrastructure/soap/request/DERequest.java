package py.com.jsifen.infrastructure.soap.request;

import jakarta.enterprise.context.ApplicationScoped;
import py.com.jsifen.infrastructure.util.soap.message.SoapIdGenerator;

@ApplicationScoped
public class DERequest {

    public String createQueryXml(String cdc) {

        String xmlId = SoapIdGenerator.generateId();

        String xmlRequest = """
            <?xml version="1.0" encoding="UTF-8"?>
            <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope"
                           xmlns:xsd="http://ekuatia.set.gov.py/sifen/xsd">
                <soap:Header/>
                <soap:Body>
                    <xsd:rEnviConsDeRequest xmlns="http://ekuatia.set.gov.py/sifen/xsd"
                                            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <dId>%s</dId>
                        <dCDC>%s</dCDC>
                    </xsd:rEnviConsDeRequest>
                </soap:Body>
            </soap:Envelope>
            """.formatted(xmlId, cdc);

        return xmlRequest;
    }

}
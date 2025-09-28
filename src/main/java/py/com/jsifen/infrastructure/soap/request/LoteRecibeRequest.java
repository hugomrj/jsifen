package py.com.jsifen.infrastructure.soap.request;

import jakarta.enterprise.context.ApplicationScoped;
import py.com.jsifen.infrastructure.util.soap.message.SoapIdGenerator;

@ApplicationScoped
public class LoteRecibeRequest {

    public String createEnvioXml(String base64Zip) {

        String xmlId = SoapIdGenerator.generateId();

        return """
        <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
            <soap:Header/>
            <soap:Body>
                <rEnvioLote xmlns="http://ekuatia.set.gov.py/sifen/xsd">
                    <dId>%s</dId>
                    <xDE>%s</xDE>
                </rEnvioLote>
            </soap:Body>
        </soap:Envelope>
        """.formatted(xmlId, base64Zip);
    }
}

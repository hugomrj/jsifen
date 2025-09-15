package py.com.jsifen.infrastructure.soap.request;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RucRequest {

    public String createQueryXml(String ruc) {
        String xmlId = generateXmlId();
        return """
            <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
                <soap:Body>
                    <xsd:rEnviConsRUC>
                        <xsd:dId>%s</xsd:dId>
                        <xsd:dRUCCons>%s</xsd:dRUCCons>
                    </xsd:rEnviConsRUC>
                </soap:Body>
            </soap:Envelope>
            """.formatted(xmlId, ruc);
    }

    public String createDetailedQueryXml(String ruc, boolean includeDetails) {
        // Versión extendida si es necesario
        return createQueryXml(ruc);
    }

    private String generateXmlId() {
        return java.util.UUID.randomUUID().toString();
    }
}
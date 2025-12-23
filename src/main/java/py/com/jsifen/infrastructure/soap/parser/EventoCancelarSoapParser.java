package py.com.jsifen.infrastructure.soap.parser;


import jakarta.enterprise.context.ApplicationScoped;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import py.com.jsifen.presentation.rest.evento.cancelar.dto.CancelarResponse;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@ApplicationScoped
public class EventoCancelarSoapParser {

    public CancelarResponse parse(String xml) {
        try {
            CancelarResponse dto = new CancelarResponse();

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setNamespaceAware(true);

            Document doc = f.newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));

            dto.setFechaProceso(text(doc, "dFecProc"));
            dto.setEstadoResultado(text(doc, "dEstRes"));
            dto.setProtocoloAut(text(doc, "dProtAut"));
            dto.setIdentificador(text(doc, "id"));
            dto.setCodigoResultado(text(doc, "dCodRes"));
            dto.setMensajeResultado(text(doc, "dMsgRes"));

            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Error parseando respuesta SOAP", e);
        }
    }

    private String text(Document doc, String tag) {
        Node n = doc.getElementsByTagNameNS("*", tag).item(0);
        return n != null ? n.getTextContent() : null;
    }
}
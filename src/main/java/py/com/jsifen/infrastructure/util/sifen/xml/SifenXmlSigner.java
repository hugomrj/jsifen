package py.com.jsifen.infrastructure.util.sifen.xml;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;
import py.com.jsifen.infrastructure.util.xml.XmlSigner;


@ApplicationScoped
public class SifenXmlSigner {

    @Inject
    XmlSigner xmlSigner;

    public Node signXml(String xml) {

        // obtener nodo raíz
        Node root = FileXML.get_root_node(xml, "rDE");

        // ubicar nodo DE
        Node n = FileXML.getElementsByTagName(root, "DE");
        Element signedElement = (Element) n;
        String signedNodeId = signedElement.getAttribute("Id");
        signedElement.setIdAttribute("Id", true);

        // firmar y retornar
        return xmlSigner.sign(root, signedNodeId );
    }
}

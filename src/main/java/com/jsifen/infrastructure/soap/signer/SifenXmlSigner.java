package com.jsifen.infrastructure.soap.signer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.jsifen.infrastructure.util.xml.parser.FileXML;
import com.jsifen.infrastructure.util.xml.signer.XmlSigner;


@ApplicationScoped
public class SifenXmlSigner {

    @Inject
    XmlSigner xmlSigner;

    public Node signXml(String xml) {

        // obtener nodo raíz
        Node root = FileXML.getRootNode(xml, "rDE");

        // ubicar nodo DE
        Node n = FileXML.getElementByTagName(root, "DE");
        Element signedElement = (Element) n;
        String signedNodeId = signedElement.getAttribute("Id");
        signedElement.setIdAttribute("Id", true);

        // firmar y retornar
        return xmlSigner.sign(root, signedNodeId );
    }
}

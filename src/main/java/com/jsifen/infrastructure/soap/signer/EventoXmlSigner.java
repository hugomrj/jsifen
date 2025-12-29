package com.jsifen.infrastructure.soap.signer;

import com.jsifen.infrastructure.config.context.EmisorContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.jsifen.infrastructure.config.sifen.SifenProperties;
import com.jsifen.infrastructure.util.xml.signer.XmlSigner;
import com.jsifen.infrastructure.util.xml.parser.FileXML;


import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class EventoXmlSigner {

    @Inject
    SifenProperties sifenProperties;

    @Inject
    EmisorContext emisorContext;


    @Inject
    XmlSigner xmlSigner;

    public Node firmar(String xml) {
        try {
            String emisor = emisorContext.getEmisor();

            // Nodo raíz a firmar
            Node root = FileXML.getRootNode(xml, "rGesEve");

            // 2️⃣ rEve con atributo Id marcado como ID XML
            Node eveNode = FileXML.getElementByTagName(root, "rEve");
            Element eveElement = (Element) eveNode;

            String signedNodeId = eveElement.getAttribute("Id");
            eveElement.setIdAttribute("Id", true);

            // 3️⃣ Keystore (default = pkcs12)
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

            try (InputStream in =
                         new FileInputStream(sifenProperties.getKeystorePath(emisor))) {
                ks.load(in, sifenProperties.getKeystorePassword(emisor).toCharArray());
            }

            String alias = ks.aliases().nextElement();

            X509Certificate certificate =
                    (X509Certificate) ks.getCertificate(alias);

            PrivateKey privateKey = (PrivateKey) ks.getKey(
                    alias,
                    sifenProperties.getKeystorePassword(emisor).toCharArray()
            );

            // 4️⃣ Firmar
            return xmlSigner.sign(
                    root,
                    signedNodeId,
                    certificate,
                    privateKey
            );

        } catch (Exception e) {
            Logger.getLogger(EventoXmlSigner.class.getName())
                    .log(Level.SEVERE, "Error firmando XML de evento", e);
            return null;
        }
    }
}

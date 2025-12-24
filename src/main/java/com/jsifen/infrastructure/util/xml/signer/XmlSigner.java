package com.jsifen.infrastructure.util.xml.signer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.w3c.dom.Node;
import com.jsifen.infrastructure.config.sifen.SifenProperties;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.*;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



@ApplicationScoped
public class XmlSigner {

    @Inject
    SifenProperties sifenProperties;

    public Node sign(Node parentNode,
                     String signedNodeId,
                     X509Certificate certificate,
                     PrivateKey privateKey)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            MarshalException, XMLSignatureException {

        XMLSignatureFactory sf = XMLSignatureFactory.getInstance("DOM");
        DigestMethod digestMethod = sf.newDigestMethod(DigestMethod.SHA256, null);
        List<Transform> transforms = List.of(
                sf.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                sf.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", (TransformParameterSpec) null));

        Reference reference = sf.newReference("#" + signedNodeId,
                digestMethod, transforms, null, null);

        SignedInfo signedInfo = sf.newSignedInfo(
                sf.newCanonicalizationMethod(
                        "http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec) null),
                sf.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null),
                Collections.singletonList(reference));

        KeyInfoFactory keyInfoFactory = sf.getKeyInfoFactory();
        X509Data x509Data = keyInfoFactory.newX509Data(Collections.singletonList(certificate));
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));

        DOMSignContext ctx = new DOMSignContext(privateKey, parentNode);
        sf.newXMLSignature(signedInfo, keyInfo).sign(ctx);

        return parentNode;
    }


    public Node sign(Node parentNode, String signedNodeId) {
        try {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            String fullPath = sifenProperties.getKeystorePath(); // ruta completa al .p12
            try (InputStream in = new FileInputStream(fullPath)) {
                ks.load(in, sifenProperties.getKeystorePassword().toCharArray());
            }

            String alias = ks.aliases().nextElement();
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
            PrivateKey pk = (PrivateKey) ks.getKey(
                    alias,
                    sifenProperties.getKeystorePassword().toCharArray()
            );

            return sign(parentNode, signedNodeId, cert, pk);

        } catch (Exception e) {
            Logger.getLogger(XmlSigner.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}

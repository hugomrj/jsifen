package py.com.jsifen.infrastructure.util.sifen.xml;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import py.com.jsifen.infrastructure.sifen.SifenProperties;
import py.com.jsifen.infrastructure.util.xml.HashUtils;
import py.com.jsifen.infrastructure.util.xml.IOUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.LinkedHashMap;

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class QrNodeBuilder {

    @Inject
    SifenProperties sifenProperties;

    public Node addQrNode(Node node ) {

        Document doc = node.getOwnerDocument();

        Element gCamFuFD = doc.createElement("gCamFuFD");
        Element dCarQR   = doc.createElement("dCarQR");
        dCarQR.appendChild(doc.createTextNode( generateQRLink(node) ));
        gCamFuFD.appendChild(dCarQR);
        node.appendChild(gCamFuFD);

        return node;
    }



    public String generateQRLink(Node root) {
        Document doc = root.getOwnerDocument();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();

        Element rootElement = (Element) root;
        queryParams.put("nVersion",
                rootElement.getElementsByTagName("dVerFor").item(0).getTextContent());

        Element deElement = (Element) rootElement.getElementsByTagName("DE").item(0);
        queryParams.put("Id", deElement.getAttribute("Id"));

        // dFeEmiDE
        String dFeEmiDE = deElement.getElementsByTagName("dFeEmiDE").item(0).getTextContent();
        LocalDateTime dateTime = LocalDateTime.parse(dFeEmiDE, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        queryParams.put("dFeEmiDE",
                HashUtils.bytesToHex(dateTime.format(formatter).getBytes(StandardCharsets.UTF_8)));

        // gDatRec
        Element gDatRec = (Element) deElement.getElementsByTagName("gDatRec").item(0);
        String iNatRec = gDatRec.getElementsByTagName("iNatRec").item(0).getTextContent();
        String iTiOpe = gDatRec.getElementsByTagName("iTiOpe").item(0).getTextContent();
        NodeList dNumIDRecList = gDatRec.getElementsByTagName("dNumIDRec");
        String dNumIDRec = dNumIDRecList.getLength() > 0 ? dNumIDRecList.item(0).getTextContent() : null;

        if (Integer.parseInt(iNatRec) == 1) {
            queryParams.put("dRucRec",
                    gDatRec.getElementsByTagName("dRucRec").item(0).getTextContent());
        } else if (Integer.parseInt(iTiOpe) != 4 && dNumIDRec != null) {
            queryParams.put("dNumIDRec", dNumIDRec);
        } else {
            queryParams.put("dNumIDRec", "0");
        }

        // Totales
        String iTiDE = deElement.getElementsByTagName("iTiDE").item(0).getTextContent();
        if (Integer.parseInt(iTiDE) != 7) {
            queryParams.put("dTotGralOpe",
                    deElement.getElementsByTagName("dTotGralOpe").item(0).getTextContent());

            String iTImp = deElement.getElementsByTagName("iTImp").item(0).getTextContent();
            if (Integer.parseInt(iTImp) == 1 || Integer.parseInt(iTImp) == 5) {
                queryParams.put("dTotIVA",
                        deElement.getElementsByTagName("dTotIVA").item(0).getTextContent());
            } else {
                queryParams.put("dTotIVA", "0");
            }
        } else {
            queryParams.put("dTotGralOpe", "0");
            queryParams.put("dTotIVA", "0");
        }

        // Cantidad de ítems
        queryParams.put("cItems",
                String.valueOf(deElement.getElementsByTagName("gCamItem").getLength()));


        // Digest
        String digestValue = doc.getElementsByTagName("DigestValue").item(0).getTextContent();
        byte[] digestValueBytes = digestValue.getBytes(StandardCharsets.UTF_8);
        queryParams.put("DigestValue", HashUtils.bytesToHex(digestValueBytes));

        queryParams.put("IdCSC", sifenProperties.getIdCsc());

        String urlParams = IOUtils.buildUrlParams(queryParams);
        String hashedParams = HashUtils.sha256Hex(urlParams + sifenProperties.getCsc());
        return sifenProperties.getUrlConsultaQr() + urlParams + "&cHashQR=" + hashedParams;
    }



}
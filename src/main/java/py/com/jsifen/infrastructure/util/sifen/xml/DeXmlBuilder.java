package py.com.jsifen.infrastructure.util.sifen.xml;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import py.com.jsifen.domain.de.gen.DeComplemento;
import py.com.jsifen.domain.de.mapping.Serializacion;
import py.com.jsifen.domain.de.structure.DeXmlElement;
import py.com.jsifen.domain.de.structure.DeXmlStructure;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;
import py.com.jsifen.infrastructure.util.xml.HashUtils;
import py.com.jsifen.infrastructure.util.xml.IOUtils;
import py.com.jsifen.infrastructure.util.xml.StringUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestScoped
public class DeXmlBuilder {

    @Inject
    SifenPropierties sifenPropierties;

    private DeXmlStructure estructura = new DeXmlStructure();
    private DeComplemento complemento = new DeComplemento();

    //private Configuracion configuracion;

    /*
    private String declaracion = "xmlns=\"http://ekuatia.set.gov.py/sifen/xsd\" "
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"https://ekuatia.set.gov.py/sifen/xsd "
            + "siRecepDE_v150.xsd\"";
    */
    private String declaracion = """
    xmlns="http://ekuatia.set.gov.py/sifen/xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="https://ekuatia.set.gov.py/sifen/xsd siRecepDE_v150.xsd"
    """;


    private Serializacion serializacion = new Serializacion();

    private Node root_node;
/*
    public DeXmlBuilder(Configuracion configuracion){
        this.configuracion = configuracion;
    }
*/

    public String  generateXml (String json )  {
        this.serializacion.setJson(json);

     //   this.estructura.generar_esquema(this.serializacion, this.complemento);
        this.estructura.generar_esquema(this.serializacion, null);


        String ret = "";
        ret = ret + "<"+this.estructura.root.getNombre()+" "
                + this.declaracion + " >";


        if (this.estructura.root.getHijos().size() > 0) {
            for ( int i=0; i < this.estructura.root.getHijos().size() ; i++) {

                ret =  ret +
                        this.buildElementValue(this.estructura.root.getHijos().get(i));
            }
        }

        ret = ret + "</"+this.estructura.root.getNombre()+">";

        return ret ;
    }


    public Node  signXml (String xml )  {

        /*
        //this.parsear(xml);
        root_node = FileXML.get_root_node(xml, "rDE");
        //root_node = xmlDocument.getElementsByTagName("rDE").item(0);

        Signature signature = new Signature();
        signature.configuracion = this.configuracion;

        //Node n = this.getElementsByTagName(this.getRoot_node(), "DE");
        Node n = FileXML.getElementsByTagName(this.getRoot_node(), "DE");
        Element signedElement = (Element) n;
        String signedNodeId = signedElement.getAttribute("Id");
        signedElement.setIdAttribute("Id", true);

        signature.signedNodeId = signedNodeId;
        Node firmado = signature.sign(this.getRoot_node());

        return firmado;

         */
        return null;
    }


    public String  buildElementValue ( DeXmlElement elemento )  {

        String ret = "";

        ret = ret + "<"+elemento.getNombre();
        if (elemento.getAtributos().size() > 0){
            ret = ret + elemento.strAtributos();
        }
        ret = ret + ">";

        if (elemento.getHijos().size() > 0) {
            for ( int i=0; i < elemento.getHijos().size() ; i++) {
                ret =  ret + this.buildElementValue(elemento.getHijos().get(i));
            }
        }
        else {

            String valor = elemento.getValor();
            if (valor == null){
                if (elemento.getAtributos().size() > 0){
                    ret = ret.substring(0, ret.trim().length()-1 );
                    ret = ret.trim() + "/>";
                }
                else{
                    ret = "";
                }
            }
            else{
                ret = ret + valor;
            }
        }

        // ver si exite una condicion
        if (ret.trim().equals("")){
            ret = "";
        }
        else{
            if (ret.substring(ret.trim().length()-2, ret.trim().length()).equals("/>")) {

                if (elemento.getHijos().size() > 0){
                    ret = ret+ "</"+elemento.getNombre()+">";
                }
            }
            else{
                ret = ret+ "</"+elemento.getNombre()+">";
            }
        }

        //ret = etiqueta_inicio + etiqueta_valor  +  etiqueta_cierre ;
        ret = ret.trim();

        String xmlString = ret.trim();
        // Expresión regular para el formato deseado
        String pattern = "<[a-zA-Z0-9]+></[a-zA-Z0-9]+>";
        // Crear el objeto Pattern
        Pattern regex = Pattern.compile(pattern);
        // Crear el objeto Matcher
        Matcher matcher = regex.matcher(xmlString);
        // Verificar si coincide con el formato deseado
        if (matcher.matches()) {
            ret = "";
        }

        return ret ;
    }




    public Node getRoot_node() {
        return root_node;
    }


    public void setComplemento(DeComplemento complemento) {
        this.complemento = complemento;
    }





    public Node addQrNode (Node node) {

        // Obtener el objeto Document al que pertenece el nodo raíz
        Document doc = node.getOwnerDocument();

        // Crear el nuevo nodo gCamFuFD
        Element gCamFuFD = doc.createElement("gCamFuFD");

        // Crear el nuevo nodo dCarQR
        Element dCarQR = doc.createElement("dCarQR");
        dCarQR.appendChild(doc.createTextNode(this.generateQRLink(node)));
        gCamFuFD.appendChild(dCarQR);

        // Agregar el nuevo nodo gCamFuFD al nodo raíz
        node.appendChild(gCamFuFD);

        return node;
    }





    private String generateQRLink(Node root) {

        Document doc = root.getOwnerDocument();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();


        Element rootElement = (Element) root;
        NodeList dVerForNl = rootElement.getElementsByTagName("dVerFor");
        String dVerForValue = dVerForNl.item(0).getTextContent();
        queryParams.put("nVersion", dVerForValue);


        NodeList DEnl = rootElement.getElementsByTagName("DE");
        Element deElement = (Element) DEnl.item(0);
        String idValue = deElement.getAttribute("Id");
        queryParams.put("Id", idValue);


        // Obtener el valor de "dFeEmiDE"
        NodeList dFeEmiDENodes = deElement.getElementsByTagName("dFeEmiDE");
        String dFeEmiDE = dFeEmiDENodes.item(0).getTextContent();
        LocalDateTime dateTime_dFeEmiDE = LocalDateTime.parse(dFeEmiDE, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        queryParams.put("dFeEmiDE", HashUtils.bytesToHex(
                dateTime_dFeEmiDE.format(formatter).getBytes(StandardCharsets.UTF_8)));



        // Obtener el elemento gDatRec
        Element gDatRecElement = (Element) deElement.getElementsByTagName("gDatRec").item(0);
        String iNatRecValue = gDatRecElement.getElementsByTagName("iNatRec").item(0).getTextContent();
        String iTiOpeValue = gDatRecElement.getElementsByTagName("iTiOpe").item(0).getTextContent();


        String dNumIDRecValue = null;
        NodeList dNumIDRecList = gDatRecElement.getElementsByTagName("dNumIDRec");
        if (dNumIDRecList.getLength() > 0) {
            dNumIDRecValue = dNumIDRecList.item(0).getTextContent();
        }



        if (Integer.parseInt(iNatRecValue) == 1) {
            String dRucRecValue = gDatRecElement.getElementsByTagName("dRucRec").item(0).getTextContent();
            queryParams.put("dRucRec", dRucRecValue);
        }
        else if (Integer.parseInt(iTiOpeValue)!= 4
                && dNumIDRecValue != null) {
            queryParams.put("dNumIDRec", dNumIDRecValue);
        }
        else {
            queryParams.put("dNumIDRec", "0");
        }


        NodeList iTiDENodes = deElement.getElementsByTagName("iTiDE");
        String iTiDEValue = iTiDENodes.item(0).getTextContent();


        if (Integer.parseInt(iTiDEValue) != 7) {

            NodeList dTotGralOpeNodes = deElement.getElementsByTagName("dTotGralOpe");
            String dTotGralOpeValue = dTotGralOpeNodes.item(0).getTextContent();

            queryParams.put("dTotGralOpe", dTotGralOpeValue);

            NodeList iTImpNodes = deElement.getElementsByTagName("iTImp");
            String iTImpValue = iTImpNodes.item(0).getTextContent();


            if (Integer.parseInt(iTImpValue) == 1 || Integer.parseInt(iTImpValue) == 5 ){

                NodeList dTotIVANodes = deElement.getElementsByTagName("dTotIVA");
                String dTotIVAValue = dTotIVANodes.item(0).getTextContent();
                queryParams.put("dTotIVA", dTotIVAValue);
            }
            else {
                queryParams.put("dTotIVA", "0");
            }
        }
        else {
            queryParams.put("dTotGralOpe", "0");
            queryParams.put("dTotIVA", "0");
        }

        NodeList gCamItemNodes = deElement.getElementsByTagName("gCamItem");
        queryParams.put("cItems", String.valueOf(gCamItemNodes.getLength()));


        NodeList digestValueNodes = doc.getElementsByTagName("DigestValue");
        String digestValue = digestValueNodes.item(0).getTextContent();
        byte[] digestValuebyte = Base64.getEncoder().encode( Base64.getDecoder().decode(digestValue));
        queryParams.put("DigestValue", HashUtils.bytesToHex(digestValuebyte) );


        queryParams.put("IdCSC", sifenPropierties.getIdCsc());




        String urlParamsString = IOUtils.buildUrlParams(queryParams);

/*
        String hashedParams = HashUtils.sha256Hex(urlParamsString + this.configuracion.getCSC() );

        String ret = this.configuracion.getUrlConsultaQr() + urlParamsString + "&cHashQR=" + hashedParams;
*/

//        return ret;
        return "";
    }


    public String getTag (String xmlData, String tag ) {

        String ret = "";

        try {
            //String xmlData = "<root><dCarQR>Valor del campo dCarQR</dCarQR></root>";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlData.getBytes()));

            //NodeList nodeList = document.getElementsByTagName("dCarQR");
            NodeList nodeList = document.getElementsByTagName(tag);


            if (nodeList.getLength() > 0) {

                Element element = (Element) nodeList.item(0);
                String campoTag = element.getTextContent();

                //System.out.println("Campo dCarQR: " + campoDCarQR);
                ret = campoTag;

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            return ret;
        }



    }



    public void setEstructura(DeXmlStructure estructura) {
        this.estructura = estructura;
    }


    public String getCDC (String xmlData ) {

        String ret = "";

        try {
            // Crear el parser para XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlData)));

            // Obtener el valor del atributo "Id" del elemento "DE"
            NodeList deNodes = doc.getElementsByTagName("DE");
            if (deNodes.getLength() > 0) {
                Element deElement = (Element) deNodes.item(0);
                String idValue = deElement.getAttribute("Id");
                ret = idValue;
            } else {
                ret = "error";
            }
        } catch (Exception e) {
            ret = "error";
        }
        finally{
            return ret;
        }
    }

}






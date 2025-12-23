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
import py.com.jsifen.infrastructure.sifen.SifenProperties;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestScoped
public class DeXmlBuilder {

    @Inject
    SifenProperties sifenProperties;

    @Inject
    DeComplemento complegen;

    @Inject
    Serializacion serializacion;

    @Inject
    DeXmlStructure xmlEstructura;


    public SifenProperties getSifenPropierties() { return sifenProperties; }
    public DeComplemento getComplegen() { return complegen; }
    public Serializacion getSerializacion() { return serializacion; }
    public DeXmlStructure getXmlEstructura() { return xmlEstructura; }



    //private DeXmlStructure estructura = new DeXmlStructure();
    //private DeComplemento complemento = new DeComplemento();

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

    //private Node root_node;

/*
    public DeXmlBuilder(Configuracion configuracion){
        this.configuracion = configuracion;
    }
*/




    public String generateXml(String jsonInput, String cdc) {

        serializacion.setJson(jsonInput);

        DeXmlElement rootElement = xmlEstructura.generar_esquema(cdc);

        String xmlOutput = "";
        xmlOutput += "<" + rootElement.getNombre() + " " + this.declaracion + " >";

        if (!rootElement.getHijos().isEmpty()) {
            for (int i = 0; i < rootElement.getHijos().size(); i++) {
                xmlOutput += this.buildElementValue(rootElement.getHijos().get(i));
            }
        }
        xmlOutput += "</" + rootElement.getNombre() + ">";

        return xmlOutput;
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






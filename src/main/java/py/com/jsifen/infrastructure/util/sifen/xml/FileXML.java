package py.com.jsifen.infrastructure.util.sifen.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class FileXML {

    public static Node getRootNode (String xml, String root)   {
        xml = xml.replaceAll(">[\\s\r\n]*<", "><");

        // Parseamos el xml
        Document xmlDocument = null ;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            xmlDocument = builder.parse(new InputSource(new StringReader(xml)));

            NodeList xn = xmlDocument.getChildNodes();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            //throw SifenExceptionUtil.xmlParsingError("Se produjo un error al parsear el archivo XML. Formato incorrecto.");
            System.err.println("Se produjo un error al parsear el archivo XML. Formato incorrecto.");
        }

        // Obtenemos el nodo principal
        Node rootNode = xmlDocument.getElementsByTagName(root).item(0);

        return rootNode;
    }



    public static String xmlToString (Node xml){

        StringWriter sw = new StringWriter();
        try {

            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(xml), new StreamResult(sw));

        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }

        return sw.toString();
    }


    public static Node getElementByTagName(Node node, String tag){

        Element element = (Element) node;
        NodeList nodeList = element.getElementsByTagName(tag);

        if (nodeList.getLength() == 1){
            Node nodo = nodeList.item(0);
            return nodo;
        }
        else{
            return null;
        }
    }

}

package com.jsifen.infrastructure.util.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class XmlUtils {

    private XmlUtils() {}

    public static String obtenerCdcDesdeXml(Node nodo) {
        if (nodo instanceof Element element) {

            // Buscar el nodo DE
            var nodeList = element.getElementsByTagName("DE");

            if (nodeList.getLength() == 0) {
                return "";
            }

            Element de = (Element) nodeList.item(0);
            return de.getAttribute("Id");
        }
        return "";
    }


    public static String obtenerNumeroFacturaDesdeXml(Node nodo) {
        if (nodo instanceof Element element) {

            var nodeList = element.getElementsByTagName("gTimb");
            if (nodeList.getLength() == 0) {
                return "";
            }

            Element gTimb = (Element) nodeList.item(0);

            String est = gTimb.getElementsByTagName("dEst").item(0).getTextContent();
            String pun = gTimb.getElementsByTagName("dPunExp").item(0).getTextContent();
            String num = gTimb.getElementsByTagName("dNumDoc").item(0).getTextContent();

            return est + "-" + pun + "-" + num;
        }
        return "";
    }



    public static Integer obtenerTipoDocumentoDesdeXml(Node nodo) {
        if (nodo instanceof Element element) {

            var nodeList = element.getElementsByTagName("iTiDE");
            if (nodeList.getLength() == 0) {
                return null;
            }

            return Integer.valueOf(nodeList.item(0).getTextContent());
        }
        return null;
    }


    public static String obtenerFechaEmisionDesdeXml(Node nodo) {
        if (nodo instanceof Element element) {

            var nodeList = element.getElementsByTagName("dFeEmiDE");
            if (nodeList.getLength() == 0) {
                return "";
            }

            return nodeList.item(0).getTextContent();
        }
        return "";
    }








}

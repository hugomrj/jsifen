package com.jsifen.infrastructure.util.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class XmlUtils {

    private XmlUtils() {}

    public static String obtenerCdcDesdeXml(Node nodo) {
        if (nodo instanceof Element element) {
            return element.getAttribute("Id");
        }
        throw new IllegalArgumentException("No se pudo obtener el CDC del XML");
    }
}

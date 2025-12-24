package com.jsifen.infrastructure.soap.processor;

import com.jsifen.infrastructure.util.xml.StringUtils;
import com.jsifen.infrastructure.util.xml.parser.XmlJsonConverter;

public final class SifenResponseProcessor {


    public static String cleanXmlForJson(String responseXML) {
        if (responseXML == null) return null;

        String output = responseXML;

        // reemplazos simples
        output = output.replace("&lt;", "<");
        output = output.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        output = StringUtils.decodeEntities(output);

        // caso específico cuando SIFEN devuelve "CDC encontrado" -> extraer <DE>...</DE>
        if (output.contains("<ns2:dMsgRes>CDC encontrado</ns2:dMsgRes>")) {
            int ini = output.indexOf("<DE");
            if (ini >= 0) {
                int fin = output.indexOf("</DE>", ini);
                if (fin > ini) {
                    output = output.substring(ini, fin + "</DE>".length());
                } // si no encuentra fin, deja el output como está (no lanzar excepción aquí)
            }
        } else {
            // remover prefijos namespace que entorpecen la conversión genérica
            output = output.replace("ns2:", "")
                    .replace(":ns2", "")
                    .replace("env:", "")
                    .replace(":env", "");
        }

        return output.trim();
    }

    public static String toJson(String responseXML) {
        String cleaned = cleanXmlForJson(responseXML);
        return XmlJsonConverter.convertXmlToJson(cleaned);
    }

}

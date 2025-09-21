package py.com.jsifen.infrastructure.util.xml;

import org.json.JSONObject;
import org.json.XML;

public abstract class  XmlJsonConverter {

    public static String convertXmlToJson(String xmlString) {
        JSONObject jsonObject = XML.toJSONObject(xmlString);
        String jsonString = jsonObject.toString();
        return jsonString;
    }

    public static String decodeEntities(String input) {
        if (input == null) return null;
        input = input.replaceAll("&#225;", "á")
                .replaceAll("&#233;", "é")
                .replaceAll("&#237;", "í")
                .replaceAll("&#243;", "ó")
                .replaceAll("&#250;", "ú")
                .replaceAll("&#209;", "Ñ")
                .replaceAll("amp;", "");
        return input;
    }

}

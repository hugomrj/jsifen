package py.com.jsifen.infrastructure.util.xml;


import org.json.JSONObject;
import org.json.XML;

public final class XmlJsonConverter {

    public static String convertXmlToJson(String xmlString) {
        JSONObject jsonObject = XML.toJSONObject(xmlString);
        return jsonObject.toString();
    }
}

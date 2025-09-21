package py.com.jsifen.infrastructure.util.soap.message;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;

public abstract class SoapBodyExtractor {
    public static String extractBody(String soapResponse) {
        try {
            String cleaned = soapResponse.replace("ns2:", "").replace("env:", "");
            try (JsonReader reader = Json.createReader(new StringReader(cleaned))) {
                JsonObject jsonObject = reader.readObject();
                if (jsonObject.containsKey("Envelope")) {
                    JsonObject envelope = jsonObject.getJsonObject("Envelope");
                    if (envelope.containsKey("Body")) {
                        return envelope.get("Body").toString();
                    }
                }
                return cleaned;
            }
        } catch (Exception e) {
            return soapResponse.replace("ns2:", "").replace("env:", "");
        }
    }
}
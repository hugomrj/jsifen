package py.com.jsifen.infrastructure.adapter;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import org.json.JSONObject;
import org.json.XML;
import py.com.jsifen.domain.repository.DERepository;
import py.com.jsifen.domain.repository.RucRepository;
import py.com.jsifen.infrastructure.soap.client.DEClient;
import py.com.jsifen.infrastructure.soap.client.RucClient;
import py.com.jsifen.infrastructure.util.sifen.validation.SifenDvCalculator;
import py.com.jsifen.infrastructure.util.soap.message.SoapBodyExtractor;
import py.com.jsifen.infrastructure.util.xml.XmlJsonConverter;

import java.net.http.HttpResponse;

@ApplicationScoped
public class DERepositoryImpl implements DERepository {

    @Inject
    DEClient deClient;

    @Override
    public String buscarPorCDC(String cdc) {
        try {
            HttpResponse<String> httpResponse = deClient.consultaDE(cdc);
            int statusCode = httpResponse.statusCode();
            String xmlOutput = httpResponse.body();

            // Decodifica
            xmlOutput = httpResponse.body().replaceAll("&lt;", "<");
            xmlOutput = XmlJsonConverter.decodeEntities(xmlOutput);

            return xmlOutput;

        } catch (Exception e) {
            e.printStackTrace();
            return "<error>" +
                    "<mensaje>Error consultando RUC: " + e.getMessage() + "</mensaje>" +
                    "</error>";
        }
    }
}

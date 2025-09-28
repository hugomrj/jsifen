package py.com.jsifen.infrastructure.adapter;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.json.JSONObject;
import org.json.XML;
import py.com.jsifen.domain.repository.FacturaRepository;
import py.com.jsifen.infrastructure.soap.client.DEClient;
import py.com.jsifen.infrastructure.soap.client.LoteClient;

import java.io.StringReader;
import java.net.http.HttpResponse;

@ApplicationScoped
public class FacturaRepositoryImpl implements FacturaRepository {

    @Inject
    LoteClient loteClient;


    @Override
    public JsonObject enviarFactura(String xml) {
        // Lógica real: enviar el XML a un servicio externo o persistirlo
        HttpResponse<String> httpResponse = loteClient.recibeLote(xml);

        int statusCode = httpResponse.statusCode();
        String xmlOutput = httpResponse.body();

        JSONObject json = XML.toJSONObject(xmlOutput);
        JsonObject jakartaJson = Json.createReader(
                new StringReader(json.toString())).readObject();

        return jakartaJson;
    }
}
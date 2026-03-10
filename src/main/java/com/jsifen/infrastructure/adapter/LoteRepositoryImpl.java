package com.jsifen.infrastructure.adapter;


import com.jsifen.presentation.rest.consulta.lote.dto.request.ConsultarLoteRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.json.JSONObject;
import org.json.XML;
import com.jsifen.domain.repository.LoteRepository;
import com.jsifen.infrastructure.soap.client.lote.LoteClient;

import java.io.StringReader;
import java.net.http.HttpResponse;

@ApplicationScoped
public class LoteRepositoryImpl implements LoteRepository {

    @Inject
    LoteClient loteClient;

    @Override
    public JsonObject buscarPorLote (String lote) {
        try {

            HttpResponse<String> httpResponse = loteClient.consultaLote(lote);
            int statusCode = httpResponse.statusCode();
            String xmlOutput = httpResponse.body();

            JSONObject json = XML.toJSONObject(xmlOutput);
            JsonObject jakartaJson = Json.createReader(
                    new StringReader(json.toString())).readObject();

            return jakartaJson;

        } catch (Exception e) {
            e.printStackTrace();
            return Json.createObjectBuilder()
                    .add("error", "Error consultando RUC: " + e.getMessage())
                    .build();
        }
    }
}

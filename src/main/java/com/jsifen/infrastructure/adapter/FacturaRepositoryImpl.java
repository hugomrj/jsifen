package com.jsifen.infrastructure.adapter;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.json.JSONObject;
import org.json.XML;
import com.jsifen.domain.repository.FacturaRepository;
import com.jsifen.infrastructure.soap.client.lote.LoteClient;

import java.io.StringReader;
import java.net.http.HttpResponse;

@ApplicationScoped
public class FacturaRepositoryImpl implements FacturaRepository {

    @Inject
    LoteClient loteClient;

    @Override
    public JsonObject enviarFactura(String xml) {

        HttpResponse<String> httpResponse = loteClient.recibeLote(xml);

        int statusCode = httpResponse.statusCode();
        String xmlOutput = httpResponse.body();

        JSONObject json = XML.toJSONObject(xmlOutput);

        // 🔧 Forzar lote como String para evitar redondeo
        JSONObject res = json
                .getJSONObject("env:Envelope")
                .getJSONObject("env:Body")
                .getJSONObject("ns2:rResEnviLoteDe");

        String lote = res.get("ns2:dProtConsLote").toString();
        res.put("ns2:dProtConsLote", lote);

        JsonObject jakartaJson = Json.createReader(
                new StringReader(json.toString())).readObject();

        return jakartaJson;
    }


}
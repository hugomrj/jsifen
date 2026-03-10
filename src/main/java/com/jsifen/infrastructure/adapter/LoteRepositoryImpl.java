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
    public JsonObject buscarPorLote(String lote) {
        try {
            System.out.println("🌐 [HTTP] Enviando petición a la SET para Lote: " + lote);

            HttpResponse<String> httpResponse = loteClient.consultaLote(lote);
            int statusCode = httpResponse.statusCode();

            System.out.println("📡 [HTTP] Respuesta recibida. Status Code: " + statusCode);

            if (statusCode != 200) {
                System.out.println("⚠️ [SET] La API respondió con error: " + httpResponse.body());
            }

            String xmlOutput = httpResponse.body();

            // Conversión de XML a JSON (org.json)
            JSONObject json = XML.toJSONObject(xmlOutput);

            // Conversión al estándar Jakarta para el resto de tu app
            JsonObject jakartaJson = Json.createReader(
                    new StringReader(json.toString())).readObject();

            System.out.println("✅ [JSIFEN] XML convertido a JSON exitosamente.");
            return jakartaJson;

        } catch (Exception e) {
            System.out.println("❌ [ERROR] Falló la comunicación o conversión del lote: " + e.getMessage());
            e.printStackTrace();
            return Json.createObjectBuilder()
                    .add("error", "Error consultando Lote: " + e.getMessage())
                    .build();
        }
    }
}


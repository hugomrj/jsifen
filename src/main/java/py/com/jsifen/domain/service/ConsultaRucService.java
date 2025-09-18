package py.com.jsifen.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;
import org.json.XML;
import py.com.jsifen.infrastructure.soap.client.RucClient;
import py.com.jsifen.infrastructure.soap.util.SifenUtil;
import py.com.jsifen.infrastructure.soap.util.SoapUtil;


import java.io.StringReader;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


@ApplicationScoped
public class ConsultaRucService {

    @Inject
    RucClient rucClient;

    public JsonObject consultaRUC(String ruc) {
        try {
            // Llamada al cliente
            HttpResponse<String> httpResponse = rucClient.consultaRUC(ruc);

            // Obtener body si la respuesta es exitosa
            String xmlOutput;
            int statusCode = httpResponse.statusCode();
            if (statusCode >= 200 && statusCode <= 299) {
                xmlOutput = httpResponse.body();
            } else {
                return Json.createObjectBuilder()
                        .add("error", "HTTP error: " + statusCode)
                        .build();
            }

            // Convertir XML a org.json.JSONObject
            JSONObject jsonOrg = XML.toJSONObject(xmlOutput);

            // Limpiar texto si es necesario
            String jsonLimpio = SoapUtil.limpiarTexto(jsonOrg.toString());
            jsonOrg = new JSONObject(jsonLimpio);

            // Obtener respuesta principal
            JSONObject rRes = jsonOrg.getJSONObject("rResEnviConsRUC");
            String dMsgRes = rRes.getString("dMsgRes");

            // Obtener datos del RUC
            JSONObject xContRUC = rRes.getJSONObject("xContRUC");

            if ("RUC encontrado".equals(dMsgRes)) {
                // Calcular DV y actualizar
                String dRUCCons = xContRUC.get("dRUCCons").toString();
                String RUCdv = SifenUtil.generateDv(dRUCCons);
                xContRUC.put("RUCdv", RUCdv);
            }

            // Crear JSON reformateado usando JSON-P
            JsonObject soapData = Json.createObjectBuilder()
                    .add("datosRUC", Json.createObjectBuilder()
                            .add("estado", xContRUC.getString("dDesEstCons"))
                            .add("nombre", xContRUC.getString("dRazCons"))
                            .add("RUCdv", xContRUC.getString("RUCdv"))
                            .add("numeroRUC", xContRUC.getLong("dRUCCons"))
                            .add("facturaElectronica", xContRUC.getString("dRUCFactElec"))
                    )
                    .add("mensaje", dMsgRes)
                    .build();

            return soapData;

        } catch (Exception e) {
            e.printStackTrace();
            return Json.createObjectBuilder()
                    .add("error", "Error consultando RUC: " + e.getMessage())
                    .build();
        }
    }


}
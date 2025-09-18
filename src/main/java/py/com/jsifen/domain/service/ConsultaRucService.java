package py.com.jsifen.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
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
            HttpResponse<String> httpResponse = rucClient.consultaRUC(ruc);
            int statusCode = httpResponse.statusCode();

            if (statusCode >= 200 && statusCode <= 299) {
                String xmlOutput = httpResponse.body();
                JSONObject jsonOrg = XML.toJSONObject(xmlOutput);
                String jsonLimpio = SoapUtil.limpiarTexto(jsonOrg.toString());
                jsonOrg = new JSONObject(jsonLimpio);

                JSONObject rRes = jsonOrg.getJSONObject("rResEnviConsRUC");
                String dCodRes = rRes.getString("dCodRes");
                String dMsgRes = rRes.getString("dMsgRes");

                JsonObjectBuilder responseBuilder = Json.createObjectBuilder()
                        .add("codigoRespuesta", dCodRes)
                        .add("mensajeRespuesta", dMsgRes)
                        .add("statusCode", statusCode); // ← AGREGA AQUÍ

                if ("0502".equals(dCodRes) && "RUC encontrado".equals(dMsgRes) && rRes.has("xContRUC")) {
                    JSONObject xContRUC = rRes.getJSONObject("xContRUC");
                    String dRUCCons = xContRUC.get("dRUCCons").toString();
                    String RUCdv = SifenUtil.generateDv(dRUCCons);

                    JsonObjectBuilder datosRucBuilder = Json.createObjectBuilder()
                            .add("estado", xContRUC.getString("dDesEstCons"))
                            .add("nombre", xContRUC.getString("dRazCons"))
                            .add("RUCdv", RUCdv)
                            .add("numeroRUC", Long.parseLong(dRUCCons))
                            .add("facturaElectronica", xContRUC.getString("dRUCFactElec"))
                            .add("codigoEstado", xContRUC.getString("dCodEstCons"));

                    responseBuilder.add("datosRUC", datosRucBuilder);

                } else if ("0500".equals(dCodRes) && "RUC no existe".equals(dMsgRes)) {
                    responseBuilder.add("datosRUC", JsonValue.NULL);
                } else {
                    return Json.createObjectBuilder()
                            .add("error", "Respuesta inesperada: " + dMsgRes)
                            .add("codigo", dCodRes)
                            .add("statusCode", statusCode) // ← También aquí
                            .build();
                }

                return responseBuilder.build();

            } else {
                return Json.createObjectBuilder()
                        .add("error", "HTTP error: " + statusCode)
                        .add("statusCode", statusCode) // ← Y aquí
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Json.createObjectBuilder()
                    .add("error", "Error consultando RUC: " + e.getMessage())
                    .build();
        }
    }
}
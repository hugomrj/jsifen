package py.com.jsifen.infrastructure.adapter;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import org.json.JSONObject;
import org.json.XML;
import py.com.jsifen.domain.repository.RucRepository;
import py.com.jsifen.infrastructure.soap.client.RucClient;
import py.com.jsifen.infrastructure.util.sifen.validation.SifenDvCalculator;
import py.com.jsifen.infrastructure.util.soap.message.SoapBodyExtractor;

import java.net.http.HttpResponse;

@ApplicationScoped
public class RucRepositoryImpl implements RucRepository {

    @Inject
    RucClient rucClient;

    @Override
    public JsonObject buscarPorRuc(String ruc) {
        try {
            HttpResponse<String> httpResponse = rucClient.consultaRUC(ruc);
            int statusCode = httpResponse.statusCode();

            if (statusCode >= 200 && statusCode <= 299) {
                String xmlOutput = httpResponse.body();
                JSONObject jsonOrg = XML.toJSONObject(xmlOutput);
                String jsonLimpio = SoapBodyExtractor.extractBody(jsonOrg.toString());
                jsonOrg = new JSONObject(jsonLimpio);

                JSONObject rRes = jsonOrg.getJSONObject("rResEnviConsRUC");
                String dCodRes = rRes.getString("dCodRes");
                String dMsgRes = rRes.getString("dMsgRes");

                JsonObjectBuilder responseBuilder = Json.createObjectBuilder()
                        .add("codigoRespuesta", dCodRes)
                        .add("mensajeRespuesta", dMsgRes)
                        .add("statusCode", statusCode);

                if ("0502".equals(dCodRes) && "RUC encontrado".equals(dMsgRes) && rRes.has("xContRUC")) {
                    JSONObject xContRUC = rRes.getJSONObject("xContRUC");
                    String dRUCCons = xContRUC.get("dRUCCons").toString();
                    String RUCdv = SifenDvCalculator.generateDv(dRUCCons);

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
                            .add("statusCode", statusCode)
                            .build();
                }

                return responseBuilder.build();

            } else {
                return Json.createObjectBuilder()
                        .add("error", "HTTP error: " + statusCode)
                        .add("statusCode", statusCode)
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

package py.com.jsifen.domain.de.gen;

import jakarta.enterprise.context.RequestScoped;
import org.json.JSONObject;
import py.com.jsifen.infrastructure.time.ClienteNTP;


@RequestScoped
public class DeComplemento {

    private String Id;
    private String dDVId;
    private String cachedJson;

    public String getId() {
        return Id;
    }

    public String getdDVId() {
        return dDVId;
    }

    public String getJsonCom(String jsonEntrada) {
        if (cachedJson != null) {
            return cachedJson; // reutiliza
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dVerFor", 150);
        jsonObject.put("dDVId", this.dDVId);

        JSONObject jsonParametro = new JSONObject(jsonEntrada);

        if (jsonParametro.has("dFecFirma")) {
            jsonObject.put("dFecFirma", jsonParametro.getString("dFecFirma"));
        } else {
            jsonObject.put("dFecFirma", ClienteNTP.getTimeFormat());
        }

        jsonObject.put("dSisFact", 1);

        // guarda en el atributo para no regenerar
        this.cachedJson = jsonObject.toString();
        return this.cachedJson;
    }

    /** Permite obtener el objeto JSONObject ya generado, si lo necesitás */
    public String getCachedJson() {
        return cachedJson;
    }
}

package com.jsifen.infrastructure.sifen.xml.gen;

import jakarta.enterprise.context.RequestScoped;
import org.json.JSONObject;
import com.jsifen.infrastructure.util.time.ClienteNTP;


@RequestScoped
public class DeComplemento {

    private String Id;
    private String dDVId;

    public String getId() {
        return Id;
    }

    public String getdDVId() {
        return dDVId;
    }

    public String getJsonCom(String jsonEntrada, String cdc) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dVerFor", 150);
        jsonObject.put("dDVId", cdc.substring(cdc.length() - 1) );

        JSONObject jsonParametro = new JSONObject(jsonEntrada);

        if (jsonParametro.has("dFecFirma")) {
            jsonObject.put("dFecFirma", jsonParametro.getString("dFecFirma"));
        } else {
            jsonObject.put("dFecFirma", ClienteNTP.getTimeFormat());
        }

        jsonObject.put("dSisFact", 1);

        return jsonObject.toString();
    }


}

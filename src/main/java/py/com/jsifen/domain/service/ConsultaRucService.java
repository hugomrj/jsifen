package py.com.jsifen.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ConsultaRucService {


    public JsonObject consultar(String ruc) {
        JsonObject resultado = this.consultar("prod", ruc);;
        return resultado;
    }



    public JsonObject consultar(String ambiente, String ruc) {
        JsonObject resultado = Json.createObjectBuilder()
                .add("mensaje", "Sevicio: " + ambiente + ", ruc :"  + ruc )
                .build();
        return resultado;
    }


}
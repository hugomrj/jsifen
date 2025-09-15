package py.com.main;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import py.com.jsifen.infrastructure.sifen.ServerSifen;




public class MainJson {

    public static void main(String[] args) {
        // Crear un objeto JSON

        SifenConfigLoader loader = SifenConfigLoader.getInstance();


        loader.printConfig();


    }
}

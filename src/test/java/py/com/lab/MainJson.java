package py.com.lab;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class MainJson {

    public static void main(String[] args) {
        // Crear un objeto JSON
        JsonObject json = Json.createObjectBuilder()
                .add("mensaje", "Hola desde Jakarta JSON en main")
                .add("exito", true)
                .build();

        // Mostrar JSON como texto
        System.out.println(json.toString());
    }
}

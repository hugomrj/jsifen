package py.com.jsifen.domain.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import jakarta.json.JsonObject;

@QuarkusTest
public class ConsultaRucServiceTest {

    @Inject
    ConsultaRucService consultaRucService; // se inyecta correctamente ahora

    @Test
    public void testConsultarRucReal() {
        String ruc = "3437941";

        JsonObject resultado = consultaRucService.consultaRUC(ruc);

        System.out.println("Resultado JSON:");
        System.out.println(resultado);
    }
}
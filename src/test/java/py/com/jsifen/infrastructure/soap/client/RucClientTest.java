package py.com.jsifen.infrastructure.soap.client;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

@QuarkusTest
public class RucClientTest {

    @Inject
    RucClient rucClient;

    @Test
    public void testConsultaRUC() {
        String ruc = "3437941";
        HttpResponse<String> response = rucClient.consultaRUC(ruc);

        System.out.println("Código de estado: " + response.statusCode());
        System.out.println("Cuerpo de la respuesta:");
        System.out.println(response.body());
    }


}

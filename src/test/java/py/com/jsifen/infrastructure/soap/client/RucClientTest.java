package py.com.jsifen.infrastructure.soap.client;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import py.com.jsifen.infrastructure.soap.util.SoapUtil;

import java.net.http.HttpResponse;

@QuarkusTest
public class RucClientTest {

    @Inject
    RucClient rucClient;

    @Test
    public void testConsultaRUC() {
        String ruc = "3437941";
        HttpResponse<String> httpResponse = rucClient.consultaRUC(ruc);

        System.out.println("Código de estado: " + httpResponse.statusCode());
        System.out.println("Cuerpo de la respuesta:");
        System.out.println(httpResponse.body());


    }


}

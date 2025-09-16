package py.com.jsifen.infrastructure.soap.client;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;

@QuarkusTest
public class RucClientTest {
    @Test
    void ambienteDebeEstarDisponible() {

        RucClient ruc = new RucClient();
        System.out.println("test 2");
        ruc.buildEndpointUrl();

    }
}

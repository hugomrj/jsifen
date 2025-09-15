package py.com.main.ruc;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import py.com.jsifen.infrastructure.soap.client.RucClient;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class RucClientTest {

    @Inject
    RucClient rucClient;

    @Test
    public void testQuery() throws Exception {
        rucClient.query("3437941");
    }
}

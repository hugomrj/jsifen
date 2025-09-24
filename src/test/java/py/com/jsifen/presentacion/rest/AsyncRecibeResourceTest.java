package py.com.jsifen.presentacion.rest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Test;
import py.com.jsifen.application.usecase.de.ConsultarDEUseCase;
import py.com.jsifen.application.usecase.factura.RecibirFacturaUseCase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class AsyncRecibeResourceTest {
    @Inject
    RecibirFacturaUseCase recibirFacturaUseCase;

    @Test
    void testdeintegracion() throws Exception {

        System.out.println("test de asyn factura");


        JsonObject jsonobe = null;
        recibirFacturaUseCase.execute(jsonobe);

    }


}

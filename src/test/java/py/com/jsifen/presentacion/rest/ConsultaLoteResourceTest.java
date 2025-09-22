package py.com.jsifen.presentacion.rest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Test;
import py.com.jsifen.application.usecase.lote.ConsultaLoteUseCase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@QuarkusTest
public class ConsultaLoteResourceTest {
    @Inject
    ConsultaLoteUseCase consultaLoteUseCase;

    @Test
    void testLoteExistente() {
        JsonObject response = consultaLoteUseCase.execute("114383161976070806"); // usa un id de lote válido
        assertNotNull(response);
        //assertTrue(response.containsKey("datosLote") || response.containsKey("resultado"));
        System.out.println("Lote existente: " + response);
    }

    @Test
    void testLoteNoExistente() {
        JsonObject response = consultaLoteUseCase.execute("999999");
        /*
        assertNotNull(response);
        assertTrue(response.containsKey("datosLote") || response.containsKey("resultado")
                || response.containsKey("error"));

         */
        System.out.println("Lote no existente: " + response);
    }

    @Test
    void testLoteInvalido() {
        JsonObject response = consultaLoteUseCase.execute("0");
        /*
        assertNotNull(response);
        assertTrue(response.containsKey("error") || response.containsKey("statusCode"));

         */
        System.out.println("Lote inválido: " + response);
    }

    @Test
    void testLoteNull() {
        JsonObject response = consultaLoteUseCase.execute(null);
        /*assertNotNull(response);
        assertTrue(response.containsKey("error") || response.containsKey("statusCode"));

         */
        System.out.println("Lote null: " + response);
    }

    @Test
    public void testInyeccionDependencias() {
        assertNotNull(consultaLoteUseCase);
    }
}
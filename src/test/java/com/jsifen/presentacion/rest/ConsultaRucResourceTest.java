package com.jsifen.presentacion.rest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.json.JsonObject;
import com.jsifen.application.usecase.ruc.ConsultarRucUseCase;


@QuarkusTest
public class ConsultaRucResourceTest {

    @Inject
    ConsultarRucUseCase consultarRucUseCase;

    @Test
    void testRucExistente() {
        JsonObject response = consultarRucUseCase.execute("3437941");
        assertNotNull(response);
        assertTrue(response.containsKey("datosRUC"));
        System.out.println("RUC existente: " + response);
    }

    @Test
    void testRucNoExistente() {
        JsonObject response = consultarRucUseCase.execute("100000");
        assertNotNull(response);
        assertTrue(response.containsKey("datosRUC"));
        System.out.println("RUC no existe: " + response);
    }

    @Test
    void testRucInvalido() {
        JsonObject response = consultarRucUseCase.execute("0");
        assertNotNull(response);
        assertTrue(response.containsKey("error") || response.containsKey("statusCode"));
        System.out.println("RUC inválido: " + response);
    }

    @Test
    void testRucNull() {
        JsonObject response = consultarRucUseCase.execute(null);
        assertNotNull(response);
        System.out.println("RUC null: " + response);
    }


    @Test
    public void testInyeccionDependencias() {
        // Solo verifica que la inyección funciona
        assertNotNull(consultarRucUseCase);
    }


}
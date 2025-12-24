package com.jsifen.presentacion.rest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import com.jsifen.application.usecase.de.ConsultarDEUseCase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class ConsultaDEResourceTest {


    @Inject
    ConsultarDEUseCase consultarDEUseCase;

    @Test
    void testDEExistente() {

        //String cdc = "01800124162018001000043312025022119883154185";
        String cdc = "018001241620180010000";

        String response = consultarDEUseCase.execute(cdc);
        assertNotNull(response);
        //assertTrue(response.containsKey("datosRUC"));
        System.out.println("existente: " + response);


    }
}

package py.com.jsifen.presentacion.rest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Test;
import py.com.jsifen.application.usecase.de.ConsultarDEUseCase;
import py.com.jsifen.application.usecase.ruc.ConsultarRucUseCase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.json.JSONObject;
import org.json.XML;
import py.com.jsifen.infrastructure.util.xml.XmlJsonConverter;

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

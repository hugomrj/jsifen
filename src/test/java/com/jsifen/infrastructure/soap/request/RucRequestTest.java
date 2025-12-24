package com.jsifen.infrastructure.soap.request;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class RucRequestTest {

    @Inject
    RucRequest rucRequest;

    @Test
    public void testCreateQueryXml() {
        String ruc = "12345678-9";
        String xml = rucRequest.createQueryXml(ruc);

        System.out.println(xml); // para ver el XML generado

        // Validaciones básicas
        assertTrue(xml.contains(ruc), "El XML debe contener el RUC proporcionado");
        assertTrue(xml.contains("<soap:Envelope"), "El XML debe tener la etiqueta <soap:Envelope>");
        assertTrue(xml.contains("<soap:Body>"), "El XML debe tener la etiqueta <soap:Body>");
    }
}

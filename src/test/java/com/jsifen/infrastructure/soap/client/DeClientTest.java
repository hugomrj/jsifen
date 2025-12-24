package com.jsifen.infrastructure.soap.client;


import com.jsifen.infrastructure.soap.client.de.DEClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

@QuarkusTest
public class DeClientTest {

    @Inject
    DEClient deClient;

    @Test
    public void testConsultaDE() {
        // CDC de prueba
        String cdc = "01800124162018001000043312025022119883154185";

        HttpResponse<String> httpResponse = deClient.consultaDE(cdc);

        System.out.println("Código de estado: " + httpResponse.statusCode());
        System.out.println("Cuerpo de la respuesta:");
        System.out.println(httpResponse.body());
    }
}

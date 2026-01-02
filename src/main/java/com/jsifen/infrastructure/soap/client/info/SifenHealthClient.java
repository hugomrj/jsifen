package com.jsifen.infrastructure.soap.client.info;

import com.jsifen.infrastructure.config.context.EmisorContext;
import com.jsifen.infrastructure.config.security.SSLConfig;
import com.jsifen.infrastructure.config.sifen.ServerSifen;
import com.jsifen.infrastructure.config.sifen.SifenProperties;
import com.jsifen.infrastructure.soap.request.RucRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;



@ApplicationScoped
public class SifenHealthClient {

    @Inject
    RucRequest rucRequest;

    @Inject
    SSLConfig sslConfig;

    @Inject
    ServerSifen serverSifen;


    public HttpResponse<String> consultarRuc(String ruc, String ambiente) {
        try {
            System.out.println("=== SIFEN HEALTH CHECK ===");
            System.out.println("Ambiente recibido: " + ambiente);

            String emisor = null;
            System.out.println("Emisor: " + emisor);

            SSLContext sslContext = sslConfig.createSSLContext(emisor);
            System.out.println("SSLContext creado OK");

            HttpClient client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();

            String baseUrl = serverSifen.getServer(ambiente);
            System.out.println("Base URL: " + baseUrl);

            String endpoint = baseUrl + "/de/ws/consultas/consulta-ruc.wsdl";
            System.out.println("Endpoint final: " + endpoint);

            String xml = rucRequest.createQueryXml(ruc);
            System.out.println("XML generado:");
            System.out.println(xml);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(xml))
                    .build();

            System.out.println("Enviando request...");

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Response body:");
            System.out.println(response.body());

            return response;

        } catch (Exception e) {
            System.out.println("❌ ERROR EN HEALTH CHECK");
            e.printStackTrace();
            throw new RuntimeException("Health check SIFEN failed", e);
        }
    }




}

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

    public HttpResponse<String> consultarRuc(String ruc, String ambiente, String emisor) {
        try {
            System.out.println("=== SIFEN HEALTH CHECK ===");
            System.out.println("Ambiente: " + ambiente);
            System.out.println("Emisor: " + emisor);

            SSLContext sslContext = sslConfig.createSSLContext(emisor);

            HttpClient client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();

            String baseUrl = serverSifen.getServer(ambiente);
            String endpoint = baseUrl + "/de/ws/consultas/consulta-ruc.wsdl";

            String xml = rucRequest.createQueryXml(ruc);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(xml))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new RuntimeException("Health check SIFEN failed", e);
        }
    }
}
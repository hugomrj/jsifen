package com.jsifen.infrastructure.soap.client.ruc;

import com.jsifen.infrastructure.config.context.EmisorContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.annotation.PostConstruct;
import com.jsifen.infrastructure.config.sifen.SifenProperties;
import com.jsifen.infrastructure.soap.request.RucRequest;
import com.jsifen.infrastructure.config.security.SSLConfig;
import com.jsifen.infrastructure.config.sifen.ServerSifen;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;


@ApplicationScoped
public class RucClient {

    @Inject
    RucRequest rucRequest;

    @Inject
    SSLConfig sslConfig;

    @Inject
    ServerSifen serverSifen;

    @Inject
    SifenProperties sifenProperties;

    @Inject
    EmisorContext emisorContext;

    public HttpResponse<String> consultaRUC(String ruc) {
        try {
            String emisor = emisorContext.getEmisor();
            SSLContext sslContext = sslConfig.createSSLContext(emisor);
            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();

            String endpointUrl = buildEndpointUrl(emisor);
            String xmlRequest = rucRequest.createQueryXml(ruc);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
                    .build();

            return httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to query RUC", e);
        }
    }

    private String buildEndpointUrl(String emisor) {
        String environment = sifenProperties.getAmbiente(emisor);
        String baseUrl = serverSifen.getServer(environment);
        return baseUrl + "/de/ws/consultas/consulta-ruc.wsdl";
    }
}

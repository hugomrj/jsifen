package com.jsifen.infrastructure.soap.client.evento;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.infrastructure.config.sifen.ServerSifen;
import com.jsifen.infrastructure.config.sifen.SifenProperties;
import com.jsifen.infrastructure.config.security.SSLConfig;
import com.jsifen.infrastructure.soap.request.EventoCancelarSoapRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class CancelarClient {

    @Inject
    EventoCancelarSoapRequest eventoCancelarRequest;

    @Inject
    SSLConfig sslConfig;

    @Inject
    ServerSifen serverSifen;

    @Inject
    SifenProperties sifenProperties;

    private HttpClient httpClient;

    @PostConstruct
    void initialize() {
        this.httpClient = HttpClient.newBuilder()
                .sslContext(sslConfig.createSSLContext())
                .build();
    }

    public HttpResponse<String> cancelarEvento(String id, String mOtEve) {
        try {
            String endpointUrl = buildCancelarEventoUrl();

            String xmlRequest =
                    eventoCancelarRequest.createCancelarXml(id, mOtEve);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
                    .build();

            return httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

        } catch (Exception e) {
            throw new RuntimeException("Error cancelando evento", e);
        }
    }

    private String buildCancelarEventoUrl() {
        String environment = sifenProperties.getAmbiente();
        String baseUrl = serverSifen.getServer(environment);
        return baseUrl + "/de/ws/eventos/evento.wsdl";
    }
}
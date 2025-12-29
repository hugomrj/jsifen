package com.jsifen.infrastructure.soap.client.evento;

import com.jsifen.infrastructure.config.context.EmisorContext;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.infrastructure.config.sifen.ServerSifen;
import com.jsifen.infrastructure.config.sifen.SifenProperties;
import com.jsifen.infrastructure.config.security.SSLConfig;
import com.jsifen.infrastructure.soap.request.EventoCancelarSoapRequest;

import javax.net.ssl.SSLContext;
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

    @Inject
    EmisorContext emisorContext;

    public HttpResponse<String> cancelarEvento(String id, String mOtEve) {
        try {

            String emisor = emisorContext.getEmisor();
            SSLContext sslContext = sslConfig.createSSLContext(emisor);
            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();


            String endpointUrl = buildCancelarEventoUrl(emisor);
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

    private String buildCancelarEventoUrl(String emisor) {
        String environment = sifenProperties.getAmbiente(emisor);
        String baseUrl = serverSifen.getServer(environment);
        return baseUrl + "/de/ws/eventos/evento.wsdl";
    }
}
package com.jsifen.infrastructure.soap.client.de;

import com.jsifen.infrastructure.config.context.EmisorContext;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.infrastructure.config.sifen.ServerSifen;
import com.jsifen.infrastructure.config.sifen.SifenProperties;
import com.jsifen.infrastructure.config.security.SSLConfig;
import com.jsifen.infrastructure.soap.request.DERequest;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class DEClient {

    @Inject
    private DERequest deRequest;

    @Inject
    private SSLConfig sslConfig;

    @Inject
    private ServerSifen serverSifen;

    @Inject
    SifenProperties sifenProperties;

    @Inject
    EmisorContext emisorContext;

    public HttpResponse<String> consultaDE(String cdc) {
        try {
            String emisor = emisorContext.getEmisor();
            SSLContext sslContext = sslConfig.createSSLContext(emisor);
            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();

            String endpointUrl = buildConsultaUrl(emisor);
            String xmlRequest = deRequest.createQueryXml(cdc);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
                    .build();

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new RuntimeException("Failed to query DE: " + e.getMessage(), e);
        }
    }



    private String buildConsultaUrl( String emisor){
        String base = serverSifen.getServer(sifenProperties.getAmbiente(emisor));
        return base + "/de/ws/consultas/consulta.wsdl";
    }

    private String buildRecepcionUrl(String emisor) {
        String base = serverSifen.getServer(sifenProperties.getAmbiente(emisor));
        return base + "/de/ws/async/recibe-lote.wsdl";
    }


}

package com.jsifen.infrastructure.soap.client.lote;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.infrastructure.config.sifen.ServerSifen;
import com.jsifen.infrastructure.config.sifen.SifenProperties;
import com.jsifen.infrastructure.config.security.SSLConfig;
import com.jsifen.infrastructure.soap.request.LoteConsultaRequest;
import com.jsifen.infrastructure.soap.request.LoteRecibeRequest;
import com.jsifen.infrastructure.util.xml.IOUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@ApplicationScoped
public class LoteClient {

    @Inject
    private LoteConsultaRequest loteConsultaRequest;

    @Inject
    private LoteRecibeRequest loteRecibeRequest;

    @Inject
    private SSLConfig sslConfig;

    @Inject
    private ServerSifen serverSifen;

    @Inject
    SifenProperties sifenProperties;

    private HttpClient httpClient;

    @PostConstruct
    void initialize() {
        this.httpClient = HttpClient.newBuilder()
                .sslContext(sslConfig.createSSLContext())
                .build();
    }


    public HttpResponse<String> consultaLote(String lote) {
        try {
            String endpointUrl = buildConsultaUrl();
            String xmlRequest = loteConsultaRequest.createQueryXml(lote);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
                    .build();

            return httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new RuntimeException("Failed to query Lote: " + e.getMessage(), e);
        }
    }




    public HttpResponse<String> recibeLote(String xmlFactura) {
        try {
            // convertir zip a base64
            xmlFactura = "<rLoteDE>" + xmlFactura + "</rLoteDE>";

            byte[] zipData = IOUtils.compressXmlToZip(xmlFactura);
            String base64Zip = Base64.getEncoder().encodeToString(zipData);

            String endpointUrl = buildRecepcionUrl();
            String xmlRequest = loteRecibeRequest.createEnvioXml(base64Zip);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .header("Content-Type", "application/soap+xml;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
                .build();

            return httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new RuntimeException("Failed to send invoice: " + e.getMessage(), e);
        }
    }



    private String buildConsultaUrl() {
        String environment = sifenProperties.getAmbiente();
        String baseUrl     = serverSifen.getServer(environment);
        return baseUrl + "/de/ws/consultas/consulta-lote.wsdl";
    }

    private String buildRecepcionUrl() {
        String environment = sifenProperties.getAmbiente();
        String baseUrl     = serverSifen.getServer(environment);
        return baseUrl + "/de/ws/async/recibe-lote.wsdl";
    }


}

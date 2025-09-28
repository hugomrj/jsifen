package py.com.jsifen.infrastructure.soap.client;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.com.jsifen.infrastructure.sifen.ServerSifen;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;
import py.com.jsifen.infrastructure.soap.config.SSLConfig;
import py.com.jsifen.infrastructure.soap.request.DERequest;

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
    SifenPropierties sifenPropierties;


    private HttpClient httpClient;

    @PostConstruct
    void initialize() {
        httpClient = HttpClient.newBuilder()
                .sslContext(sslConfig.createSSLContext())
                .build();
    }

    public HttpResponse<String> consultaDE(String cdc) {
        try {
            String endpointUrl = buildConsultaUrl();
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



    private String buildConsultaUrl() {
        String base = serverSifen.getServer(sifenPropierties.getAmbiente());
        return base + "/de/ws/consultas/consulta.wsdl";
    }

    private String buildRecepcionUrl() {
        String base = serverSifen.getServer(sifenPropierties.getAmbiente());
        return base + "/de/ws/async/recibe-lote.wsdl";
    }


}

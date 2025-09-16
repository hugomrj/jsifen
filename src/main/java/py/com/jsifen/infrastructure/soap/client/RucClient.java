package py.com.jsifen.infrastructure.soap.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.annotation.PostConstruct;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;
import py.com.jsifen.infrastructure.soap.request.RucRequest;
import py.com.jsifen.infrastructure.soap.config.SSLConfig;
import py.com.jsifen.infrastructure.sifen.ServerSifen;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

@ApplicationScoped
public class RucClient {

    @Inject
    private RucRequest rucRequest;

    @Inject
    private SSLConfig sslConfig;

    /*
    @Inject
    private SifenConfig sifenConfig;
    */


    @Inject
    private ServerSifen serverSifen;

    private HttpClient httpClient;

    @PostConstruct
    void initialize() {
        this.httpClient = HttpClient.newBuilder()
                .sslContext(sslConfig.createSSLContext())
                .build();
    }

    public HttpResponse<String> query(String ruc) {
        try {
            String endpointUrl = buildEndpointUrl();
            String xmlRequest = rucRequest.createQueryXml(ruc);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
                    .build();

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new RuntimeException("Failed to query RUC: " + e.getMessage(), e);
        }
    }

    public HttpResponse<String> queryDetailed(String ruc, boolean includeDetails) {
        try {
            String endpointUrl = buildEndpointUrl();
            String xmlRequest = rucRequest.createDetailedQueryXml(ruc, includeDetails);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
                    .build();

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new RuntimeException("Failed to query RUC with details: " + e.getMessage(), e);
        }
    }

    public String buildEndpointUrl() {
        /*String environment = sifenConfig.ambiente();*/
        String environment = SifenPropierties.getInstance().getAmbiente();
        String baseUrl = serverSifen.getServer(environment);

        System.out.println(baseUrl);

        return baseUrl + "/de/ws/consultas/consulta-ruc.wsdl";
    }


}
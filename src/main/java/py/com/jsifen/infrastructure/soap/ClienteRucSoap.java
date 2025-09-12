package py.com.jsifen.infrastructure.soap;

public class ClienteRucSoap {




    public HttpResponse<String> ws_consulta_ruc( String ruc )
            throws IOException, InterruptedException, Exception {



        this.endpointUrl = "/de/ws/consultas/consulta-ruc.wsdl";
        this.endpointUrl = ServerSifen.getServer(this.configuracion.getAmbiente())
                + this.endpointUrl ;




        String owner = "";
        owner = this.configuracion.getOwner();


        // 80033703

        String xmlId = SoapUtil.xmlId() ;


        String xmlRequest = """
        <?xml version="1.0" encoding="UTF-8"?>
        <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:xsd="http://ekuatia.set.gov.py/sifen/xsd">
            <soap:Header/>
            <soap:Body>
                <xsd:rEnviConsRUC xmlns="http://ekuatia.set.gov.py/sifen/xsd"
                                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                  xsi:schemaLocation="http://ekuatia.set.gov.py/sifen/xsd/ siConsRUC_v150.xsd">
                    <xsd:dId>%s</xsd:dId>
                    <xsd:dRUCCons>%s</xsd:dRUCCons>
                </xsd:rEnviConsRUC>
            </soap:Body>
        </soap:Envelope>
        """.formatted(xmlId, ruc);





        // Load the client certificate from the keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(KEYSTORE_PATH), KEYSTORE_PASSWORD.toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, KEYSTORE_PASSWORD.toCharArray());


        // Create an SSLContext with the client certificate
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());


        // Create an HttpClient with the custom SSLContext
        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();


        // Create an HTTP request with the SOAP message as the request body
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .header("Content-Type", "application/soap+xml;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
                .build();


        // Send the HTTP request and print the response body
        HttpResponse<String> httpResponse = httpClient.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());

//System.out.println(httpResponse);

        return httpResponse;

    }





}

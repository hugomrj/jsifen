package com.jsifen.infrastructure.adapter;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.domain.repository.DERepository;
import com.jsifen.infrastructure.soap.client.de.DEClient;
import com.jsifen.infrastructure.util.xml.StringUtils;

import java.net.http.HttpResponse;

@ApplicationScoped
public class DERepositoryImpl implements DERepository {

    @Inject
    DEClient deClient;

    @Override
    public String buscarPorCDC(String cdc) {
        try {
            HttpResponse<String> httpResponse = deClient.consultaDE(cdc);
            int statusCode = httpResponse.statusCode();
            String xmlOutput = httpResponse.body();

            // Decodifica
            xmlOutput = httpResponse.body().replaceAll("&lt;", "<");
            xmlOutput = StringUtils.decodeEntities(xmlOutput);

            return xmlOutput;

        } catch (Exception e) {
            e.printStackTrace();
            return "<error>" +
                    "<mensaje>Error consultando RUC: " + e.getMessage() + "</mensaje>" +
                    "</error>";
        }
    }
}

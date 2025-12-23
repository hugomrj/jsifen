package py.com.jsifen.infrastructure.adapter;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.com.jsifen.domain.repository.EventoCancelarRepository;
import py.com.jsifen.infrastructure.soap.client.evento.CancelarClient;

import java.net.http.HttpResponse;

@ApplicationScoped
public class EventoCancelarRepositoryImpl
        implements EventoCancelarRepository {

    @Inject
    CancelarClient cancelarClient;

    @Override
    public String cancelarEvento(String cdc, String motivo) {
        try {
            HttpResponse<String> httpResponse =
                    cancelarClient.cancelarEvento(cdc, motivo);

            if (httpResponse.statusCode() >= 500) {
                throw new RuntimeException(
                        "Error HTTP " + httpResponse.statusCode()
                );
            }

            return httpResponse.body();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error llamando al servicio SOAP", e
            );
        }
    }
}

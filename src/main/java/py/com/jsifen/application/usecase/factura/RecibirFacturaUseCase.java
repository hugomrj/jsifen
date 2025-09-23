package py.com.jsifen.application.usecase.factura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import py.com.jsifen.domain.repository.FacturaRepository;
import py.com.jsifen.domain.repository.RucRepository;


@ApplicationScoped
public class RecibirFacturaUseCase {

    @Inject
    FacturaRepository facturaRepository;

    public JsonObject execute(JsonObject facturaJson) throws Exception {
        // aquí podrías validar o aplicar reglas de negocio antes/después
        return facturaRepository.enviarFactura(facturaJson);
    }


}

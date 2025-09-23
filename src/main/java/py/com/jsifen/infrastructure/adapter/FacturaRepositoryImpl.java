package py.com.jsifen.infrastructure.adapter;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonObject;
import py.com.jsifen.domain.repository.FacturaRepository;

@ApplicationScoped
public class FacturaRepositoryImpl implements FacturaRepository {

    @Override
    public JsonObject enviarFactura(JsonObject facturaJson)  {
        // lógica real o un simple retorno por ahora
        return facturaJson;
    }
}

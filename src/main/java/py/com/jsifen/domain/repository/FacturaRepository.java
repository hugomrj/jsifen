package py.com.jsifen.domain.repository;


import jakarta.json.JsonObject;

public interface FacturaRepository {
    JsonObject enviarFactura(JsonObject facturaJson);
}

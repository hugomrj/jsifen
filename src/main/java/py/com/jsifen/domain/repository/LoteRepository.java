package py.com.jsifen.domain.repository;
import jakarta.json.JsonObject;

public interface LoteRepository {
    JsonObject buscarPorLote(String lote);
}
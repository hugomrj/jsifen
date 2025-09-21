package py.com.jsifen.domain.repository;
import jakarta.json.JsonObject;

public interface RucRepository {
    JsonObject buscarPorRuc(String ruc);
}
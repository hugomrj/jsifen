package py.com.jsifen.application.usecase.ruc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import py.com.jsifen.domain.repository.RucRepository;

@ApplicationScoped
public class ConsultarRucUseCase {

    @Inject
    RucRepository rucRepository;

    public JsonObject execute(String ruc) {
        return rucRepository.buscarPorRuc(ruc);
    }
}
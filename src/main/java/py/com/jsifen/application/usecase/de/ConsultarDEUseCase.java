package py.com.jsifen.application.usecase.de;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import py.com.jsifen.domain.repository.DERepository;
import py.com.jsifen.domain.repository.RucRepository;

@ApplicationScoped
public class ConsultarDEUseCase {

    @Inject
    DERepository deRepository;

    public String execute(String cdc) {
        return deRepository.buscarPorCDC(cdc);
    }
}
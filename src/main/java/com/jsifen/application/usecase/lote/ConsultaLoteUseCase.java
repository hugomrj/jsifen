package com.jsifen.application.usecase.lote;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import com.jsifen.domain.repository.LoteRepository;


@ApplicationScoped
public class ConsultaLoteUseCase {

    @Inject
    LoteRepository loteRepository;

    public JsonObject execute(String lote) {
        return loteRepository.buscarPorLote(lote);
    }
}
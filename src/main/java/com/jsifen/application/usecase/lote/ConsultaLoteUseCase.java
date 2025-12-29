package com.jsifen.application.usecase.lote;

import com.jsifen.presentation.rest.consulta.lote.dto.request.ConsultarLoteRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.domain.repository.LoteRepository;
import jakarta.json.JsonObject;


@ApplicationScoped
public class ConsultaLoteUseCase {

    @Inject
    LoteRepository loteRepository;

    public JsonObject execute(String lote) {
        return loteRepository.buscarPorLote(lote);
    }
}
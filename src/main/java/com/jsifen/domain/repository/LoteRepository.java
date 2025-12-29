package com.jsifen.domain.repository;


import com.jsifen.presentation.rest.consulta.lote.dto.request.ConsultarLoteRequest;
import jakarta.json.JsonObject;

public interface LoteRepository {
    JsonObject buscarPorLote(String lote);
}
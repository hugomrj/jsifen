package com.jsifen.application.usecase.evento;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.domain.repository.EventoCancelarRepository;
import com.jsifen.infrastructure.soap.parser.EventoCancelarSoapParser;
import com.jsifen.presentation.rest.evento.cancelar.dto.CancelarRequest;
import com.jsifen.presentation.rest.evento.cancelar.dto.CancelarResponse;


@ApplicationScoped
public class CancelarEventoUseCase {

    @Inject
    EventoCancelarRepository cancelarRepository;

    @Inject
    EventoCancelarSoapParser parser;

    public CancelarResponse execute(CancelarRequest request) {

        String xml = cancelarRepository.cancelarEvento(
                request.getCdc(),
                request.getMotivo()
        );

        return parser.parse(xml);
    }
}
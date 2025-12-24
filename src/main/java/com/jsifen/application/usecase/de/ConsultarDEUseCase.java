package com.jsifen.application.usecase.de;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.domain.repository.DERepository;


@ApplicationScoped
public class ConsultarDEUseCase {

    @Inject
    DERepository deRepository;

    public String execute(String cdc) {
        return deRepository.buscarPorCDC(cdc);
    }
}
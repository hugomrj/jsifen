package com.jsifen.application.usecase.info;

import com.jsifen.domain.repository.RucRepository;
import com.jsifen.infrastructure.config.sifen.ServerSifen;
import com.jsifen.infrastructure.soap.client.info.SifenHealthClient;
import com.jsifen.presentation.rest.info.dto.HealthStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;

import java.net.http.HttpResponse;

@ApplicationScoped
public class SifenHealthUseCase {

    @Inject
    SifenHealthClient healthClient;

    public HealthStatus check(String env) {

        try {
            String ruc = generarRucTest();

            var response = healthClient.consultarRuc(ruc, env);

            int httpStatus = response.statusCode();
            boolean up = httpStatus >= 200 && httpStatus < 500;

            return new HealthStatus(
                    up ? "UP" : "DOWN",
                    env,
                    httpStatus
            );

        } catch (Exception e) {
            return new HealthStatus(
                    "DOWN",
                    env,
                    500
            );
        }
    }

    private String generarRucTest() {
        return "8000000" + (1 + new java.util.Random().nextInt(9));
    }
}

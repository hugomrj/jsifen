package com.jsifen.application.usecase.info;

import com.jsifen.infrastructure.soap.client.info.SifenHealthClient;
import com.jsifen.presentation.rest.info.dto.HealthStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class SifenHealthUseCase {

    @Inject
    SifenHealthClient healthClient;

    public HealthStatus check(String env, String emisor) {

        try {
            String ruc = generarRucTest();

            var response = healthClient.consultarRuc(ruc, env, emisor);

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

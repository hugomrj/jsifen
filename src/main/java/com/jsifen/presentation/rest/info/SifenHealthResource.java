package com.jsifen.presentation.rest.info;

import com.jsifen.application.usecase.info.SifenHealthUseCase;
import com.jsifen.presentation.rest.info.dto.HealthStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;


@Path("/health/sifen")
@Produces("application/json")
public class SifenHealthResource {

    @Inject
    SifenHealthUseCase sifenHealthUseCase;

    @GET
    @Path("/{env}")
    public Response check(@PathParam("env") String env) {

        HealthStatus status = sifenHealthUseCase.check(env);

        return Response
                .status("UP".equals(status.getStatus()) ? 200 : 503)
                .entity(status)
                .build();
    }
}

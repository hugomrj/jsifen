package com.jsifen.presentation.rest.info;

import com.jsifen.application.usecase.info.SifenHealthUseCase;
import com.jsifen.presentation.rest.info.dto.HealthStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/health/sifen")
@Produces("application/json")
public class SifenHealthResource {

    @Inject
    SifenHealthUseCase sifenHealthUseCase;

    @GET
    @Path("/{env}")
    public Response check(
            @PathParam("env") String env,
            @QueryParam("emisor") String emisor
    ) {

        HealthStatus status = sifenHealthUseCase.check(env, emisor);

        return Response
                .status("UP".equals(status.getStatus()) ? 200 : 503)
                .entity(status)
                .build();
    }
}


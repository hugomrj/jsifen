package com.jsifen.presentation.rest.consulta.ruc;

import com.jsifen.infrastructure.config.context.EmisorContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.jsifen.application.usecase.ruc.ConsultarRucUseCase;
import com.jsifen.presentation.rest.consulta.ruc.dto.request.ConsultaRucRequest;
import com.jsifen.presentation.rest.consulta.ruc.dto.response.ConsultarRucResult;


@Path("/consulta/ruc")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Consulta RUC")
public class ConsultaRucResource {

    @Inject
    ConsultarRucUseCase consultarRucUseCase;

    @Inject
    EmisorContext emisorContext;

    @POST
    @Operation(summary = "Consulta RUC", description = "Consulta información de un RUC")
    public Response consultarRuc(
            @HeaderParam("token") String token,
            @HeaderParam("Emisor") String emisor,
            ConsultaRucRequest request)  {
        try {

            // Si no viene empresa → usa default
            if (emisor == null || emisor.isBlank()) {
                emisor = null;
            }
            emisorContext.setEmisor(emisor);

            ConsultarRucResult response = consultarRucUseCase.execute(request.getRuc());

            return Response
                .status(Response.Status.OK)
                .entity(response)
                .build();

        } catch (Exception e) {
            // Cualquier error se devuelve como HTTP 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error interno: " + e.getMessage())
                .build();
        }
    }

}

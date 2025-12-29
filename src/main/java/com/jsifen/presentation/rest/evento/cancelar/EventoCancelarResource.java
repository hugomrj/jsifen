package com.jsifen.presentation.rest.evento.cancelar;


import com.jsifen.infrastructure.config.context.EmisorContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.jsifen.application.usecase.evento.CancelarEventoUseCase;
import com.jsifen.presentation.rest.evento.cancelar.dto.CancelarRequest;
import com.jsifen.presentation.rest.evento.cancelar.dto.CancelarResponse;


@Path("/evento/cancelar")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Evento Cancelar")
public class EventoCancelarResource {

    @Inject
    CancelarEventoUseCase cancelarEventoUseCase;

    @Inject
    EmisorContext emisorContext;

    @POST
    @Operation(
            summary = "Cancelar evento",
            description = "Envía la cancelación de un evento vía SOAP y retorna la respuesta en JSON"
    )
    public Response cancelarEvento(
            @HeaderParam("token") String token,
            @HeaderParam("Emisor") String emisor,
            CancelarRequest request
    ) {
        try {

            if (emisor == null || emisor.isBlank()) {
                emisor = null;
            }
            emisorContext.setEmisor(emisor);

            String cdc    = request.getCdc();
            String motivo = request.getMotivo();

            CancelarResponse resp = cancelarEventoUseCase.execute(request);

            return Response.ok(resp).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();
        }
    }
}


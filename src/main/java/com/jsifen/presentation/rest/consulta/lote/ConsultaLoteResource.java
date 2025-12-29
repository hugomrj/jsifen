package com.jsifen.presentation.rest.consulta.lote;


import com.jsifen.infrastructure.config.context.EmisorContext;
import com.jsifen.presentation.rest.consulta.lote.dto.request.ConsultarLoteRequest;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.jsifen.application.usecase.lote.ConsultaLoteUseCase;


@Path("/consulta/lote")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Consulta Lote")
public class ConsultaLoteResource {

    @Inject
    ConsultaLoteUseCase consultaLoteUseCase;

    @Inject
    EmisorContext emisorContext;

    @POST
    @Operation(
            summary = "Consulta por lote",
            description = "Procesa la consulta de un lote de datos"
    )
    public Response consultarLote(
            @HeaderParam("token") String token,
            @HeaderParam("Emisor") String emisor,
            ConsultarLoteRequest request
    ) {
        try {

            if (emisor == null || emisor.isBlank()) {
                emisor = null;
            }
            emisorContext.setEmisor(emisor);

            JsonObject response = consultaLoteUseCase.execute(request.getLote());

            return Response
                    .status(Response.Status.OK)
                    .entity(response)
                    .build();


        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();
        }
    }
}

package py.com.jsifen.presentation.rest.consulta;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import py.com.jsifen.application.usecase.ruc.ConsultarRucUseCase;
import py.com.jsifen.presentation.rest.consulta.dto.request.ConsultaRucRequest;
import py.com.jsifen.presentation.rest.consulta.dto.response.ConsultarRucResult;


@Path("/consulta/ruc")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Consulta RUC")
public class ConsultaRucResource {

    @Inject
    ConsultarRucUseCase consultarRucUseCase;

    @POST
    @Operation(summary = "Consulta RUC", description = "Consulta información de un RUC")
    public Response consultarRuc(ConsultaRucRequest request)  {
        try {
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

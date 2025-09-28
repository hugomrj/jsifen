package py.com.jsifen.presentation.rest.evento;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.StringReader;

@Path("/evento/cancelar")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Evento Cancelar (en construccion)")
public class EventoCancelarResource {
/*
    @Inject
    CancelarEventoUseCase cancelarEventoUseCase;

 */

    @POST
    @Operation(
            summary = "Cancelar evento",
            description = "Envía la cancelación de un evento vía SOAP y retorna la respuesta en JSON"
    )
    public Response cancelarEvento(
            @HeaderParam("token") String token,
            @RequestBody(
                    description = "Datos del evento a cancelar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.STRING),
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = "{ \"id\":\"123\", \"mOtEve\":\"motivo\" }"
                            )
                    )
            )
            String json
    ) {
        try {
            JsonObject body = Json.createReader(new StringReader(json)).readObject();
            String id     = body.getString("id");
            String mOtEve = body.getString("mOtEve");

            //JsonObject resp = cancelarEventoUseCase.execute(token, id, mOtEve);

            return Response.ok(null).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();
        }
    }
}
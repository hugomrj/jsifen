package py.com.jsifen.presentation.rest;

import jakarta.inject.Inject;
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
import py.com.jsifen.domain.service.ConsultaRucService;

import java.io.StringReader;


@Path("/consulta/ruc")

@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Consulta RUC")  // nombre amigable en Swagger
public class ConsultaRucResource {

    @Inject
    ConsultaRucService consultaRucService;  // CDI se encarga de instanciarlo

    @POST
    @Operation(summary = "Consulta RUC", description = "Consulta información de un RUC")
    public Response consultarRuc(
            @RequestBody(
                    description = "JSON con el RUC a consultar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.STRING),
                            examples = @ExampleObject(
                                    name = "Ejemplo RUC",
                                    value = "{ \"ruc\": \"12345678\" }"
                            )
                    )
            )
            String json
    ) {
        try {

            JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
            String ruc = jsonObject.getString("ruc");

            // Llama al service que devuelve un DTO
            JsonObject json_response = consultaRucService.consultar( ruc );

            return Response
                    .status(Response.Status.OK)
                    .entity(json_response)
                    .build();

        } catch (Exception e) {
            // Cualquier error se devuelve como HTTP 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();
        }
    }



}

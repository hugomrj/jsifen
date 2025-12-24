package com.jsifen.presentation.rest.consulta.lote;


import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.jsifen.application.usecase.lote.ConsultaLoteUseCase;

import java.io.StringReader;


@Path("/consulta/lote")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Consulta Lote")
public class ConsultaLoteResource {

    @Inject
    ConsultaLoteUseCase consultaLoteUseCase;
    @POST
    @Operation(
            summary = "Consulta por lote",
            description = "Procesa la consulta de un lote de datos"
    )
    public Response consultarLote(
            @RequestBody(
                    description = "JSON con el número de lote a consultar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    requiredProperties = {"lote"}
                            ),
                            examples = @ExampleObject(
                                    name = "Ejemplo Lote",
                                    value = "{ \"lote\": \"114383161976070806\" }"
                            )
                    )
            )
            String json
    ) {
        try {

            JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
            String lote = jsonObject.getString("lote");

            JsonObject jsonResponse = consultaLoteUseCase.execute( lote );

            return Response
                    .status(Response.Status.OK)
                    .entity(jsonResponse)
                    .build();


        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();
        }
    }
}

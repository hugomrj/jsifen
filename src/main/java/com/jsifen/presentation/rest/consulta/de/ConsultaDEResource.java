package com.jsifen.presentation.rest.consulta.de;


import com.jsifen.infrastructure.config.context.EmisorContext;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.jsifen.application.usecase.de.ConsultarDEUseCase;
import com.jsifen.infrastructure.soap.processor.SifenResponseProcessor;
import com.jsifen.infrastructure.util.xml.parser.XmlJsonConverter;

import java.io.StringReader;

@Path("/consulta/de")
@Produces(MediaType.TEXT_XML)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Consulta Documento Electrónico")
public class ConsultaDEResource {

    @Inject
    ConsultarDEUseCase consultarDEUseCase;

    @Inject
    EmisorContext emisorContext;

    // ---------  Respuesta en XML -----------------
    @POST
    @Path("/xml")
    @Operation(
            summary = "Consulta Documento Electrónico (XML)",
            description = "Consulta un documento electrónico por CDC (Código de Control)"
    )
    public Response consultaDExml(
            @HeaderParam("Emisor") String emisor,
            @RequestBody(
                    description = "JSON con el CDC del documento a consultar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.STRING),
                            examples = @ExampleObject(
                                name = "Ejemplo CDC",
                                value = "{\"cdc\": \"12345678901234567890123456789012345678901234\"}"
                            )
                    )
            )
            String json
    ){
        try {

            // Si no viene empresa → usa default
            if (emisor == null || emisor.isBlank()) {
                emisor = null;
            }
            emisorContext.setEmisor(emisor);


            JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
            String cdc = jsonObject.getString("cdc");

            String xmlResponse = consultarDEUseCase.execute(cdc) ;

            return Response
                .status(Response.Status.OK)
                .entity(xmlResponse)
                .build();

        } catch (Exception e) {
            // Cualquier error se devuelve como HTTP 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error interno: " + e.getMessage())
                .build();
        }

    }



    // --------- Respuesta en JSON -----------------
    @POST
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Consulta Documento Electrónico (JSON)",
        description = "Consulta un documento electrónico por CDC (Código de Control) y retorna JSON"
    )
    public Response consultaDEjson(
        @RequestBody(
            description = "JSON con el CDC del documento a consultar",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = SchemaType.STRING),
                examples = @ExampleObject(
                    name = "Ejemplo CDC",
                    value = "{\"cdc\": \"12345678901234567890123456789012345678901234\"}"
                )
            )
        )
        String json
    ){
        try {
            JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
            String cdc = jsonObject.getString("cdc");

            // Obtiene la respuesta en XML del caso de uso
            String xmlResponse = consultarDEUseCase.execute(cdc);

            String cleanedXml = SifenResponseProcessor.cleanXmlForJson(xmlResponse);
            String jsonResponse = XmlJsonConverter.convertXmlToJson(cleanedXml);

            return Response
                .status(Response.Status.OK)
                .entity(jsonResponse)
                .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Json.createObjectBuilder()
                    .add("error", e.getMessage())
                        .build())
                .build();
        }
    }

}

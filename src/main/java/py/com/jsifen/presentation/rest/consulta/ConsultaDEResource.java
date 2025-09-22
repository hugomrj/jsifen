package py.com.jsifen.presentation.rest.consulta;


import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import py.com.jsifen.application.usecase.de.ConsultarDEUseCase;
import py.com.jsifen.infrastructure.soap.processor.SifenResponseProcessor;
import py.com.jsifen.infrastructure.util.xml.XmlJsonConverter;

import java.io.StringReader;

@Path("/consulta/de")
@Produces(MediaType.TEXT_XML)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Consulta Documento Electrónico")
public class ConsultaDEResource {

    @Inject
    ConsultarDEUseCase consultarDEUseCase;

    // ---------  Respuesta en XML -----------------
    @POST
    @Path("/xml")
    @Operation(
            summary = "Consulta Documento Electrónico (XML)",
            description = "Consulta un documento electrónico por CDC (Código de Control)"
    )
    public Response consultaDExml(
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

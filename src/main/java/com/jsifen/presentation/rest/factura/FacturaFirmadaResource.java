package com.jsifen.presentation.rest.factura;

import com.jsifen.application.usecase.factura.GenerarXmlFacturaUseCase;
import com.jsifen.infrastructure.config.context.EmisorContext;
import com.jsifen.presentation.rest.factura.dto.FacturaFirmadaResponse;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.StringReader;

@Path("/factura/xml/firmar")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Factura XML")
public class FacturaFirmadaResource {

    @Inject
    GenerarXmlFacturaUseCase generarXmlFacturaUseCase;

    @Inject
    EmisorContext emisorContext;

    @POST
    @Operation(
            summary = "Genera y devuelve el XML firmado",
            description = "Recibe el JSON de la factura y retorna únicamente el XML firmado"
    )
    public Response firmarXml(
            @HeaderParam("token") String token,
            @HeaderParam("Emisor") String emisor,
            String json
    ) {
        try {
            if (emisor == null || emisor.isBlank()) {
                emisor = null;
            }

            emisorContext.setEmisor(emisor);

            JsonObject jsonObject = Json.createReader(
                    new StringReader(json)
            ).readObject();


            FacturaFirmadaResponse response = generarXmlFacturaUseCase.ejecutar(jsonObject);

            return Response
                    .status(Response.Status.OK)
                    .entity(response)
                    .build();


        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al generar XML firmado: " + e.getMessage())
                    .build();
        }
    }
}

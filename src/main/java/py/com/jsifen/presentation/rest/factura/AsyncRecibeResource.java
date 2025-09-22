package py.com.jsifen.presentation.rest.factura;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/factura/async/recibe")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Factura Async")
public class AsyncRecibeResource {

    @POST
    @Operation(
            summary = "Recepción asíncrona de factura",
            description = "Procesa una factura electrónica en modo asíncrono"
    )
    public Response asyncRecibe(String json) {
        // implementación futura
        return Response.ok().build();
    }
}
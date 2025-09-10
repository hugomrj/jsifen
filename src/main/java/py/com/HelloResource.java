package py.com.jsifen;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/hello")
@Tag(name = "Hello", description = "Endpoint de prueba Hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
            summary = "Saludo simple",
            description = "Devuelve un saludo de prueba"
    )
    public String hello() {
        return "Hello from Quarkus!";
    }
}

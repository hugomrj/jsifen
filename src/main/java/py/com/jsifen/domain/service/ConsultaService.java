package py.com.jsifen.domain.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ConsultaService {

    public Response consultar(String ambiente, String token, String json) {
        // Solo devuelve un mensaje de prueba según el ambiente
        String mensaje = "Servicio llamado en ambiente: " + ambiente +
                " con token: " + token +
                " y json: " + json;

        return Response
                .ok()
                .entity(mensaje)
                .build();
    }
}
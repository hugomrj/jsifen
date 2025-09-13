package py.com.jsifen.infrastructure.confg;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuración base de JAX-RS.
 * Todos los endpoints REST estarán bajo "/api".
 */
@ApplicationPath("/api")
public class ApiApplication extends Application {
    // No se necesita código adicional por ahora
}

package py.com.jsifen.infrastructure.sifen;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped // ← Convertir en bean de CDI
public class ServerSifen {

    // Cambiar a constantes (final) y mayúsculas (convención)
    private static final String PRODUCCION = "https://sifen.set.gov.py";
    private static final String TEST = "https://sifen-test.set.gov.py";

    // Quitar static para poder inyectar como bean
    public String getServer(String ambiente) {
        // Mejorar validaciones y quitar variable temporal
        if ("prod".equalsIgnoreCase(ambiente)) { // ← Cambiar "soap" a "produccion"
            return PRODUCCION;
        }
        else if ("test".equalsIgnoreCase(ambiente)) {
            return TEST;
        }
        // Agregar manejo de error
        throw new IllegalArgumentException("Ambiente no válido: " + ambiente);
    }

    // Métodos adicionales útiles
    public String getProduccionServer() {
        return PRODUCCION;
    }

    public String getTestServer() {
        return TEST;
    }
}
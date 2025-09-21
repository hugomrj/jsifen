package py.com.jsifen.infrastructure.adapter;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import py.com.jsifen.domain.repository.DERepository;

@QuarkusTest
class DERepositoryImplTest {

    @Inject
    DERepository deRepository; // ← inyecta la implementación concreta

    @Test
    void buscarPorCDC_SoloEjecutarEImprimir() {
        String cdc = "01800124162018001000043312025022119883154185"; // ejemplo de CDC válido
        String resultado = deRepository.buscarPorCDC(cdc);

        System.out.println("\n=== Respuesta CDC ===");
        System.out.println(resultado);
        System.out.println("=====================\n");

        // Sin asserts — solo ejecutar e imprimir, como prefieres
    }
}
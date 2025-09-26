package py.com.jsifen.infrastructure.config;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;

@QuarkusTest
public class SifenPropiertiesTest {


    @Inject
    SifenPropierties sifenPropierties;


    @Test
    void imprimeConfig() {
        // Esto va a ejecutar printConfig y mandar la salida a la consola
        sifenPropierties.printConfig();

    }
}
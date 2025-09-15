package py.com.jsifen.infrastructure.sifen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class SifenPropierties {

    private static final String DEFAULT_PATH = "/sifen.properties";
    private final Properties properties = new Properties();

    // Instancia única (singleton) usando inicialización perezosa con bloque sincronizado
    private static volatile SifenPropierties instance;

    private SifenPropierties() {
        try (InputStream in = getClass().getResourceAsStream(DEFAULT_PATH)) {
            if (in == null) {
                throw new RuntimeException("No se pudo encontrar el archivo de configuración: " + DEFAULT_PATH);
            }
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar el archivo de configuración: " + DEFAULT_PATH, e);
        }
    }

    // Devuelve la instancia única (thread-safe)
    public static SifenPropierties getInstance() {
        if (instance == null) {                // primer check
            synchronized (SifenPropierties.class) {
                if (instance == null) {        // segundo check dentro del synchronized
                    instance = new SifenPropierties();
                }
            }
        }
        return instance;
    }

    public String getKeystorePath() {
        return properties.getProperty("sifen.keystore.path");
    }

    public String getKeystorePassword() {
        return properties.getProperty("sifen.keystore.password");
    }

    public String getAmbiente() {
        return properties.getProperty("sifen.ambiente");
    }

    public String getIdCsc() {
        return properties.getProperty("sifen.id-csc");
    }

    public String getCsc() {
        return properties.getProperty("sifen.csc");
    }

    public Properties getAllProperties() {
        return properties;
    }

    public void printConfig() {
        System.out.println("===== Sifen Configuration =====");
        System.out.println("Keystore Path   : " + getKeystorePath());
        System.out.println("Keystore Password: " + getKeystorePassword());
        System.out.println("Ambiente        : " + getAmbiente());
        System.out.println("ID CSC          : " + getIdCsc());
        System.out.println("CSC             : " + getCsc());
        System.out.println("================================");
    }
}

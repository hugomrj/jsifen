package py.com.jsifen.infrastructure.sifen;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@ApplicationScoped
public class SifenPropierties {

    private static final String DEFAULT_PATH = "/sifen.properties";
    private final Properties properties = new Properties();

    /** Campos calculados según el ambiente */
    private final String urlConsultaQr;
    private final String csc;

    public SifenPropierties() {
        try (InputStream in = getClass().getResourceAsStream(DEFAULT_PATH)) {
            if (in == null) {
                throw new RuntimeException(
                        "No se pudo encontrar el archivo de configuración: " + DEFAULT_PATH);
            }
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(
                    "No se pudo cargar el archivo de configuración: " + DEFAULT_PATH, e);
        }

        // Solo test o prod
        String ambiente = getAmbiente();
        if ("prod".equalsIgnoreCase(ambiente)) {
            this.urlConsultaQr = "https://ekuatia.set.gov.py/consultas/qr?";
            this.csc = getCsc(); // usa el del archivo
        } else if ("test".equalsIgnoreCase(ambiente)) {
            this.urlConsultaQr = "https://ekuatia.set.gov.py/consultas-test/qr?";
            this.csc = "ABCD0000000000000000000000000000";
        } else {
            throw new IllegalStateException("Ambiente inválido: " + ambiente +
                    " (use 'prod' o 'test')");
        }

    }


    public String getKeystorePath() { return properties.getProperty("sifen.keystore.path"); }
    public String getKeystorePassword() { return properties.getProperty("sifen.keystore.password"); }
    public String getAmbiente() { return properties.getProperty("sifen.ambiente", ""); }
    public String getIdCsc() { return properties.getProperty("sifen.id-csc", ""); }
    public String getCsc() { return properties.getProperty("sifen.csc", ""); }

    /** Valores efectivos según el ambiente */
    public String getUrlConsultaQr() { return urlConsultaQr; }
    public String getEffectiveCsc() { return csc; }

    public Properties getAllProperties() { return properties; }

    public void printConfig() {
        System.out.println("===== Sifen Configuration =====");
        System.out.println("Keystore Path    : " + getKeystorePath());
        System.out.println("Keystore Password: " + getKeystorePassword());
        System.out.println("Ambiente         : " + getAmbiente());
        System.out.println("ID CSC           : " + getIdCsc());
        System.out.println("CSC (efectivo)   : " + getEffectiveCsc());
        System.out.println("URL Consulta QR  : " + getUrlConsultaQr());
        System.out.println("================================");
    }

    private String getProperty(String key, String def) {
        return properties.getProperty(key, def);
    }
}
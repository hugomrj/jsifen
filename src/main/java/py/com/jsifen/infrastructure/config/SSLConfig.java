package py.com.jsifen.infrastructure.soap.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.com.jsifen.infrastructure.sifen.SifenPropierties;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;

@ApplicationScoped
public class SSLConfig {

    private SifenPropierties sifenPropierties;

    public SSLContext createSSLContext() {
        try {
            // ✅ Usar valores de la configuración general


            /*
            String keystorePath = sifenPropierties.keystorePath();
            String keystorePassword = sifenPropierties.keystorePassword();
            char[] passwordChars = keystorePassword.toCharArray();
*/
            String keystorePath = "";
            char[] passwordChars = null;
            String keystorePassword = "";

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(keystorePath), passwordChars);


            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());

            return sslContext;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create SSL context: " + e.getMessage(), e);
        }
    }



}
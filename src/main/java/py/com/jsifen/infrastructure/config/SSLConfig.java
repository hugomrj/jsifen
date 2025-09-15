package py.com.jsifen.infrastructure.soap.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;

@ApplicationScoped
public class SSLConfig {

    private final SifenConfig sifenConfig;

    public SSLContext createSSLContext() {
        try {
            // ✅ Usar valores de la configuración general

            String keystorePath = sifenConfig.keystorePath();
            String keystorePassword = sifenConfig.keystorePassword();
            char[] passwordChars = keystorePassword.toCharArray();

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

    public void setSifenConfig(SifenConfig sifenConfig) {
        this.sifenConfig = sifenConfig;
    }


}
package com.jsifen.infrastructure.config.security;

import com.jsifen.infrastructure.config.sifen.SifenProperties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;


@ApplicationScoped
public class SSLConfig {

    @Inject
    SifenProperties sifenProperties;

    public SSLContext createSSLContext(String emisor) {
        try {
            String keystorePath = sifenProperties.getKeystorePath(emisor);
            String keystorePassword = sifenProperties.getKeystorePassword(emisor);

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(
                    new FileInputStream(keystorePath),
                    keystorePassword.toCharArray()
            );

            KeyManagerFactory kmf =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keystorePassword.toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            return sslContext;

        } catch (Exception e) {
            throw new RuntimeException("Error creando SSLContext para emisor " + emisor, e);
        }
    }
}

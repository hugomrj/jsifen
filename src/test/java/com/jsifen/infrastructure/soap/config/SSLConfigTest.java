package com.jsifen.infrastructure.soap.config;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import javax.net.ssl.SSLContext;
import com.jsifen.infrastructure.config.security.SSLConfig;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class SSLConfigTest {

    //SSLConfig sslConfig = new SSLConfig();;
    @Inject
    SSLConfig sslConfig; // Quarkus inyecta automáticamente

    @Test
    public void testCreateSSLContext() {
        SSLContext sslContext = sslConfig.createSSLContext();
        assertNotNull(sslContext, "SSLContext should not be null");
        System.out.println("SSL Protocol: " + sslContext.getProtocol());
    }
}
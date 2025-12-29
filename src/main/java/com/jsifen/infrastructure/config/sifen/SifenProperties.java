package com.jsifen.infrastructure.config.sifen;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



@ApplicationScoped
public class SifenProperties {

    private static final String DEFAULT_CLASSPATH = "/sifen.properties";
    private static final String SYSTEM_PROPERTY = "sifen.config";

    private final Properties properties = new Properties();

    public SifenProperties() {
        try {
            InputStream in;

            // 1️⃣ Prioridad: archivo externo (-Dsifen.config=...)
            String externalPath = System.getProperty(SYSTEM_PROPERTY);

            if (externalPath != null && !externalPath.isBlank()) {
                System.out.println("📄 Cargando configuración externa: " + externalPath);
                in = new FileInputStream(externalPath);
            } else {
                // 2️⃣ Fallback: recurso dentro del JAR
                System.out.println("📄 Cargando configuración interna: " + DEFAULT_CLASSPATH);
                in = getClass().getResourceAsStream(DEFAULT_CLASSPATH);
            }

            if (in == null) {
                throw new RuntimeException(
                        "No se pudo encontrar el archivo de configuración (externo o interno)");
            }

            properties.load(in);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando sifen.properties", e);
        }
    }

    // =========================
    // LÓGICA BASE
    // =========================

    private String get(String cliente, String key) {
        if (cliente == null || cliente.isBlank()) {
            return properties.getProperty("sifen." + key);
        }
        return properties.getProperty("sifen." + cliente + "." + key);
    }

    // =========================
    // GETTERS
    // =========================

    public String getAmbiente(String cliente) {
        return get(cliente, "ambiente");
    }

    public String getIdCsc(String cliente) {
        return get(cliente, "id-csc");
    }

    public String getCsc(String cliente) {
        return get(cliente, "csc");
    }

    public String getKeystorePath(String cliente) {
        return get(cliente, "keystore.path");
    }

    public String getKeystorePassword(String cliente) {
        return get(cliente, "keystore.password");
    }

    // =========================
    // VALORES CALCULADOS
    // =========================

    public String getUrlConsultaQr(String cliente) {
        String ambiente = getAmbiente(cliente);

        if ("prod".equalsIgnoreCase(ambiente)) {
            return "https://ekuatia.set.gov.py/consultas/qr?";
        }
        if ("test".equalsIgnoreCase(ambiente)) {
            return "https://ekuatia.set.gov.py/consultas-test/qr?";
        }

        throw new IllegalStateException("Ambiente inválido: " + ambiente);
    }

    public String getEffectiveCsc(String cliente) {
        String ambiente = getAmbiente(cliente);

        if ("test".equalsIgnoreCase(ambiente)) {
            return "ABCD0000000000000000000000000000";
        }
        return getCsc(cliente);
    }

    // =========================
    // DEBUG
    // =========================

    public void printConfig(String cliente) {
        System.out.println("===== SIFEN CONFIG =====");
        System.out.println("Cliente          : " + (cliente == null ? "(default)" : cliente));
        System.out.println("Ambiente         : " + getAmbiente(cliente));
        System.out.println("ID CSC           : " + getIdCsc(cliente));
        System.out.println("CSC efectivo     : " + getEffectiveCsc(cliente));
        System.out.println("Keystore Path    : " + getKeystorePath(cliente));
        System.out.println("URL QR           : " + getUrlConsultaQr(cliente));
        System.out.println("========================");
    }
}
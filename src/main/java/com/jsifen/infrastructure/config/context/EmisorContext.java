package com.jsifen.infrastructure.config.context;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class EmisorContext {

    private String emisor;

    public String getEmisor() {
        System.out.println("Emisor actual: " + emisor);
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }
}

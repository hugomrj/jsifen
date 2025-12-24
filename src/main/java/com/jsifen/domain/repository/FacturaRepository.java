package com.jsifen.domain.repository;

import jakarta.json.JsonObject;

public interface FacturaRepository {
    JsonObject enviarFactura(String xml);
}
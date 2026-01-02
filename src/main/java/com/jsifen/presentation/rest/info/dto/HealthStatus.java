package com.jsifen.presentation.rest.info.dto;

public class HealthStatus {

    private String status;
    private String ambiente;
    private int httpStatus;
    private String timestamp;

    public HealthStatus(String status, String ambiente, int httpStatus) {
        this.status = status;
        this.ambiente = ambiente;
        this.httpStatus = httpStatus;
        this.timestamp = java.time.OffsetDateTime.now().toString();
    }


    public String getStatus() {
        return status;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

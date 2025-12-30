package com.jsifen.presentation.rest.consulta.lote.dto.response;


import java.util.List;

public class ConsultaLoteResponse {

    private String codigoLote;
    private String mensajeLote;
    private List<Resultado> resultados;

    public ConsultaLoteResponse(
            String codigoLote,
            String mensajeLote,
            List<Resultado> resultados
    ) {
        this.codigoLote = codigoLote;
        this.mensajeLote = mensajeLote;
        this.resultados = resultados;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public String getMensajeLote() {
        return mensajeLote;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    // 👇 Clase interna
    public static class Resultado {

        private String cdc;
        private String estado;
        private Integer codigoError;
        private String mensajeError;

        public Resultado(
                String cdc,
                String estado,
                Integer codigoError,
                String mensajeError
        ) {
            this.cdc = cdc;
            this.estado = estado;
            this.codigoError = codigoError;
            this.mensajeError = mensajeError;
        }

        public String getCdc() {
            return cdc;
        }

        public String getEstado() {
            return estado;
        }

        public Integer getCodigoError() {
            return codigoError;
        }

        public String getMensajeError() {
            return mensajeError;
        }
    }
}
package py.com.jsifen.presentation.rest.evento.cancelar.dto;

public class CancelarResponse {

    private String fechaProceso;
    private String estadoResultado;
    private String protocoloAut;
    private String identificador;
    private String codigoResultado;
    private String mensajeResultado;

    public String getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(String fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getEstadoResultado() {
        return estadoResultado;
    }

    public void setEstadoResultado(String estadoResultado) {
        this.estadoResultado = estadoResultado;
    }

    public String getProtocoloAut() {
        return protocoloAut;
    }

    public void setProtocoloAut(String protocoloAut) {
        this.protocoloAut = protocoloAut;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getCodigoResultado() {
        return codigoResultado;
    }

    public void setCodigoResultado(String codigoResultado) {
        this.codigoResultado = codigoResultado;
    }

    public String getMensajeResultado() {
        return mensajeResultado;
    }

    public void setMensajeResultado(String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }
}
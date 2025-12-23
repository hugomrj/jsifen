package py.com.jsifen.presentation.rest.consulta.ruc.dto.response;

public class DatosRuc {

    private String estado;
    private String nombre;
    private String RUCdv;
    private Integer numeroRUC;
    private String facturaElectronica;
    private String codigoEstado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRUCdv() {
        return RUCdv;
    }

    public void setRUCdv(String RUCdv) {
        this.RUCdv = RUCdv;
    }

    public Integer getNumeroRUC() {
        return numeroRUC;
    }

    public void setNumeroRUC(Integer numeroRUC) {
        this.numeroRUC = numeroRUC;
    }

    public String getFacturaElectronica() {
        return facturaElectronica;
    }

    public void setFacturaElectronica(String facturaElectronica) {
        this.facturaElectronica = facturaElectronica;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
}

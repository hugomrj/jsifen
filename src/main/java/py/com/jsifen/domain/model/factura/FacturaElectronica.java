package py.com.jsifen.domain.model.factura;




public class FacturaElectronica {

    private String cdc;
    private String linkQR;
    private String json;

    public String getCdc() {
        return cdc;
    }

    public void setCdc(String cdc) {
        this.cdc = cdc;
    }

    public String getLinkQR() {
        return linkQR;
    }

    public void setLinkQR(String linkQR) {
        this.linkQR = linkQR;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
package py.com.jsifen.domain.de.structure;


public class XmlAttribute {

    private String nombre;
    private String valor;

    public XmlAttribute( ){
    }

    public XmlAttribute(String nombre, String valor ){
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
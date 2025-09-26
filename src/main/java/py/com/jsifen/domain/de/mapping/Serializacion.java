package py.com.jsifen.domain.de.mapping;


import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

@RequestScoped
public class Serializacion {

    private ArrayList<FieldMapping> elementos = new ArrayList <FieldMapping>() ;
    private String path =  this.setResoursePath();
    private String json ;


    public String setResoursePath ( )  {
        String str =  "";
        str  = this.getClass().getResource(this.getClass().getSimpleName() + ".class").getPath();
        str = str.substring(0, str.indexOf("WEB-INF") + "WEB-INF".length() ) ;
        str = str  +"/configuracion/";
        this.path = str;

        return str;
    }



    public void adaptador() {
        this.setResoursePath();
        File file = new File(this.path + "relacion" );
        if (file.exists()) {
            this.readfile(file);
        }
        else{
            System.out.println("No existe archivo de relacion");
        }
    }


/*
    public void generar_esquema_prueba( String json ) {

        JsonObject jsonobject = new JsonObject();

        jsonobject = this.gson.fromJson(json, JsonObject.class);

        String ff = gson.toJson(jsonobject.get("Detalles"));

        try {

            JsonArray hh = jsonobject.get("campos_items").getAsJsonArray();

        } catch (java.lang.NullPointerException e) {

            System.out.println( "dfsafsa" );

        }

    }

 */


    public void readfile (  File file ) {

        BufferedReader br = null;
        try {

            file.createNewFile();
            br = new BufferedReader(new FileReader(file));
            String texto = br.readLine();

            while(texto != null)
            {
                String sSubCadena = texto.trim();
                if (sSubCadena.length() != 0 ){
                    //planfinanciero = leerLinea (sSubCadena, planfinanciero ) ;
                    this.add(sSubCadena);
                }
                texto = br.readLine();
            }

        }


        catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }


        finally {
            try {
                if(br != null)
                    br.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }


    public void add (String linea){
        // ver si ya se puede cargar el json
        FieldMapping fieldMapping = new FieldMapping();
        // divisor :
        String etiqueta_xml = "";
        String etiqueta_json = "";

        String[] arraylinea = linea.split(":");

        etiqueta_xml = arraylinea[0].trim();
        etiqueta_json = arraylinea[1].trim();

        fieldMapping.setXml(etiqueta_xml);
        fieldMapping.setJson(etiqueta_json);

        this.getElementos().add(fieldMapping);

    }

    public ArrayList <FieldMapping> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList <FieldMapping> elementos) {
        this.elementos = elementos;
    }

    public String getCampoJson (String xml){

        String ret = "";
        ArrayList<FieldMapping> ele = this.getElementos();
        for (FieldMapping fm : ele) {

            if (fm.getXml().equals(xml)){
                ret = fm.getJson();
                break;
            }
        }
        return ret;
    }


    public String getValorJson(String campo) {
        if (this.json == null || this.json.isEmpty()) {
            return "";
        }

        try {
            JSONObject objeto = new JSONObject(this.json);
            // optString devuelve "" si la clave no existe
            return objeto.optString(campo, "");
        } catch (org.json.JSONException e) {
            // logueá si querés, pero devolvé cadena vacía para mantener el comportamiento
            return "";
        }
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

}


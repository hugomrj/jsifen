package py.com.jsifen.infrastructure.soap.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public abstract class SoapUtil {

    public static String xmlId () {

        LocalDateTime startDateTime = LocalDateTime.of(2023, 6, 1, 0, 0, 0);
        LocalDateTime currentDateTime = LocalDateTime.now();
        long milliseconds = ChronoUnit.MILLIS.between(startDateTime, currentDateTime);

        // Ajustar el valor para que esté en el rango de 1 a 999,999,999
        milliseconds = milliseconds % 999_999_999 + 1;

        String ret = String.valueOf(milliseconds);
        return ret ;
    }




/*
    public static String convertXmlToJson(String xmlString) {
        JSONObject jsonObject = XML.toJSONObject(xmlString);
        String jsonString = jsonObject.toString();
        return jsonString;
    }


    public static String limpiarTexto(String texto) {
        texto = texto.replace("ns2:", "");
        texto = texto.replace("env:", "");

        String json = texto;


        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        JsonObject bodyObject = jsonObject.getAsJsonObject("Envelope").getAsJsonObject("Body");


        //return texto;
        return bodyObject.toString();
    }
*/



/*

    public static byte[] XmlToZip(String str) throws IOException {

        String fileName = "DE_" + new SimpleDateFormat("ddMMyyyy").format(new Date());

        // Utilizamos ByteArrayOutputStream en lugar de File y FileOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(byteArrayOutputStream);

        ZipEntry entry = new ZipEntry(fileName + ".xml");
        out.putNextEntry(entry);

        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.closeEntry();
        out.close();

        return byteArrayOutputStream.toByteArray();
    }




    public static boolean verifyBase64AsZip(String base64Zip) throws IOException {
        byte[] zipData = Base64.getDecoder().decode(base64Zip);
        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipData))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            // Si entry es nulo, significa que no se pudo leer una entrada del archivo ZIP, lo que indica que no es válido.
            return entry != null;
        }
    }




    public static String extractContentFromZip(byte[] zipData) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipData))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            if (entry != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                return outputStream.toString(StandardCharsets.UTF_8);
            }
        }
        return null;
    }

*/


}



package py.com.jsifen.infrastructure.util.xml;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class IOUtils {
    private IOUtils() {}

    public static byte[] getByteArrayFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = inputStream.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        return result.toByteArray();
    }

    public static String buildUrlParams(Map<String, String> params) {
        if (params == null || params.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : params.entrySet()) {
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static byte[] compressXmlToZip(String xml) throws IOException {
        
        String fileName = "DE_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
        File zip = File.createTempFile(fileName, ".zip");
        try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(zip.toPath()))) {
            out.putNextEntry(new ZipEntry(fileName + ".xml"));
            out.write(xml.getBytes(StandardCharsets.UTF_8));
            out.closeEntry();
        }
        return Files.readAllBytes(zip.toPath());
    }
}
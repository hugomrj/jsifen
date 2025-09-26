package py.com.jsifen.infrastructure.util.xml;


public final class StringUtils {
    private StringUtils() {}

    public static String leftPad(String text, char padChar, int length) {
        if (text == null) text = "";
        return String.format("%" + length + "s", text).replace(' ', padChar);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static String decodeEntities(String input) {
        if (input == null) return null;
        return input.replaceAll("&#225;", "á")
                .replaceAll("&#233;", "é")
                .replaceAll("&#237;", "í")
                .replaceAll("&#243;", "ó")
                .replaceAll("&#250;", "ú")
                .replaceAll("&#209;", "Ñ")
                .replaceAll("amp;", "");
    }

    public static String unescapeXML(String xml) {
        return xml.replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&apos;", "'")
                .replace("&quot;", "\"");
    }
}
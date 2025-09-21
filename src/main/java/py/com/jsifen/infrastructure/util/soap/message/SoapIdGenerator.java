package py.com.jsifen.infrastructure.util.soap.message;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class SoapIdGenerator {
    public static String generateId() {
        LocalDateTime startDateTime = LocalDateTime.of(2025, 6, 1, 0, 0, 0);
        LocalDateTime currentDateTime = LocalDateTime.now();
        long milliseconds = ChronoUnit.MILLIS.between(startDateTime, currentDateTime);
        milliseconds = milliseconds % 999_999_999 + 1;
        return String.valueOf(milliseconds);
    }
}

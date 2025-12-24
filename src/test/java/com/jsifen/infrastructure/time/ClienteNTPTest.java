package com.jsifen.infrastructure.time;



import com.jsifen.infrastructure.util.time.ClienteNTP;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClienteNTPTest {

    @Test
    void testGetTimeReturnsRecentDate() {
        Date now = new Date();
        Date ntpTime = ClienteNTP.getTime();

        assertNotNull(ntpTime, "El tiempo NTP no debe ser null");

        // Asegura que la fecha devuelta no sea muy anterior (por ejemplo, más de 1 día de diferencia)
        long diffMillis = Math.abs(now.getTime() - ntpTime.getTime());
        assertTrue(diffMillis < 86_400_000L, "La hora NTP debe ser razonablemente cercana a la actual");
    }



    @Test
    void testGetTimeFormatHasCorrectPattern() {
        String formatted = ClienteNTP.getTimeFormat();

        // Muestra en consola la hora obtenida
        System.out.println("Hora NTP formateada: " + formatted);

        assertNotNull(formatted);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        assertDoesNotThrow(() -> LocalDateTime.parse(formatted, formatter));
    }

}
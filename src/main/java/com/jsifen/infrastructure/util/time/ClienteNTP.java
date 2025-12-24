package com.jsifen.infrastructure.util.time;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public final class ClienteNTP {

    public static Date getTime() {

        String ntpServer1 = "aravo1.set.gov.py"; // Primer servidor NTP
        String ntpServer2 = "aravo2.set.gov.py"; // Segundo servidor NTP

        NTPUDPClient client = new NTPUDPClient();
        Date ret = new Date();

        try {
            client.open();

            // Primer intento
            InetAddress serverAddress1 = InetAddress.getByName(ntpServer1);
            TimeInfo timeInfo1 = client.getTime(serverAddress1);
            timeInfo1.computeDetails();

            // ✅ Hora oficial enviada por el servidor NTP
            ret = timeInfo1.getMessage()
                    .getTransmitTimeStamp()
                    .getDate();

        } catch (IOException e) {
            try {
                client.open();

                // Segundo intento
                InetAddress serverAddress2 = InetAddress.getByName(ntpServer2);
                TimeInfo timeInfo2 = client.getTime(serverAddress2);
                timeInfo2.computeDetails();

                ret = timeInfo2.getMessage()
                        .getTransmitTimeStamp()
                        .getDate();

            } catch (IOException ex) {
                System.out.println("Error en el segundo servidor NTP. No se pudo obtener la hora.");
            }

        } finally {
            client.close();
        }

        return ret;
    }

    public static String getTimeFormat() {
        Date ntpDate = ClienteNTP.getTime();
        Instant instant = ntpDate.toInstant();
        ZoneId zoneId = ZoneId.of("America/Asuncion");
        LocalDateTime currentDate = LocalDateTime.ofInstant(instant, zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return currentDate.format(formatter);
    }

}

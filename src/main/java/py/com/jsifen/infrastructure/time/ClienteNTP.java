package py.com.jsifen.infrastructure.time;


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

        String ntpServer1 = "aravo1.set.gov.py"; // Primer servidor NTP deseado
        String ntpServer2 = "aravo2.set.gov.py"; // Segundo servidor NTP deseado

        // Crea un cliente NTP
        NTPUDPClient client = new NTPUDPClient();


        Date ret = new Date();

        try {

            client.open();


            // Configura la dirección del servidor NTP
            InetAddress serverAddress1 = InetAddress.getByName(ntpServer1);

            // Consulta la hora al servidor NTP
            TimeInfo timeInfo1 = client.getTime(serverAddress1);
            timeInfo1.computeDetails();

            // Obtiene la hora del servidor NTP
            long serverTime1 = timeInfo1.getReturnTime();

            // Convierte la hora del servidor a un objeto Date
            Date serverDate1 = new Date(serverTime1);

            ret = serverDate1;

        }
        catch (IOException  e) {

            try {

                client.open();

                // Configura la dirección del servidor NTP
                InetAddress serverAddress2 = InetAddress.getByName(ntpServer2);

                // Consulta la hora al servidor NTP
                TimeInfo timeInfo2 = client.getTime(serverAddress2);
                timeInfo2.computeDetails();

                // Obtiene la hora del servidor NTP
                long serverTime1 = timeInfo2.getReturnTime();

                // Convierte la hora del servidor a un objeto Date
                Date serverDate2 = new Date(serverTime1);

                ret = serverDate2;

            }
            catch (IOException ex) {
                // Ocurrió un error al consultar el segundo servidor NTP
                System.out.println("Error en el segundo servidor NTP. No se pudo obtener la hora.");
            }


        }
        finally{
            client.close();
            return ret;
        }


    }


    public static String getTimeFormat() {

        Date aravo = ClienteNTP.getTime();
        Instant instant = aravo.toInstant();
        ZoneId zoneId = ZoneId.of("America/Asuncion");
        LocalDateTime currentDate = LocalDateTime.ofInstant(instant, zoneId);
        //currentDate = currentDate.minusHours(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return currentDate.format(formatter);
    }


}

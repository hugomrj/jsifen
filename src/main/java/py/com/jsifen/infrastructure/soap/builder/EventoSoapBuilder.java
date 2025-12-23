package py.com.jsifen.infrastructure.soap.builder;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventoSoapBuilder {

    public String build(String xmlId, String xmlEvento) {

        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <env:Envelope xmlns:env="http://www.w3.org/2003/05/soap-envelope">
               <env:Header/>
               <env:Body>
                  <rEnviEventoDe xmlns="http://ekuatia.set.gov.py/sifen/xsd">
                     <dId>%s</dId>
                     <dEvReg>
                        <gGroupGesEve
                           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="https://ekuatia.set.gov.py/sifen/xsd siRecepEvento_v150.xsd">
                           %s
                        </gGroupGesEve>
                     </dEvReg>
                  </rEnviEventoDe>
               </env:Body>
            </env:Envelope>
            """.formatted(xmlId, xmlEvento);
    }
}

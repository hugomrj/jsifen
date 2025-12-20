package py.com.jsifen.application.usecase.ruc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import py.com.jsifen.domain.repository.RucRepository;
import py.com.jsifen.presentation.rest.consulta.dto.response.ConsultarRucResult;
import py.com.jsifen.presentation.rest.consulta.dto.response.DatosRuc;

@ApplicationScoped
public class ConsultarRucUseCase {

    @Inject
    RucRepository rucRepository;

    public ConsultarRucResult execute(String ruc) {

        JsonObject json = rucRepository.buscarPorRuc(ruc);

        System.out.println("===== RESPUESTA RUC =====");
        System.out.println(json);
        System.out.println("=========================");

        ConsultarRucResult result = new ConsultarRucResult();

        if (json.containsKey("statusCode")) {
            result.setStatusCode(json.getInt("statusCode"));
        }

        if (json.containsKey("error")) {
            result.setError(json.getString("error"));
            return result;
        }

        result.setCodigoRespuesta(json.getString("codigoRespuesta", null));
        result.setMensajeRespuesta(json.getString("mensajeRespuesta", null));

        if (json.containsKey("datosRUC") && !json.isNull("datosRUC")) {

            JsonObject datos = json.getJsonObject("datosRUC");
            DatosRuc datosRuc = new DatosRuc(); // ✔️ misma unidad de compilación

            datosRuc.setEstado(datos.getString("estado", null));
            datosRuc.setNombre(datos.getString("nombre", null));
            datosRuc.setRUCdv(datos.getString("RUCdv", null));
            datosRuc.setNumeroRUC(
                    datos.containsKey("numeroRUC") ? datos.getInt("numeroRUC") : null
            );
            datosRuc.setFacturaElectronica(datos.getString("facturaElectronica", null));
            datosRuc.setCodigoEstado(datos.getString("codigoEstado", null));

            result.setDatosRUC(datosRuc);
        }

        return result;
    }


}
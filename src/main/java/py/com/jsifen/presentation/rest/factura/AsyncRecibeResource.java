package py.com.jsifen.presentation.rest.factura;


import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import py.com.jsifen.application.usecase.factura.RecibirFacturaUseCase;
import py.com.jsifen.domain.model.factura.FacturaElectronica;

import java.io.StringReader;


@Path("/factura/async/recibe")
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Factura Async") // nombre amigable en Swagger
public class AsyncRecibeResource {


    @Inject
    RecibirFacturaUseCase recibirFacturaUseCase;


    @POST
    @Operation(
            summary = "Recepción asíncrona de factura",
            description = "Procesa una factura electrónica en modo asíncrono"
    )
    public Response asyncRecibe(
            @RequestBody(
                    description = "JSON de factura electrónica",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT),
                            examples = @ExampleObject(
                                    name = "Ejemplo Factura",
                                    value = """
{
  "iTipEmi": 1,
  "dDesTipEmi": "Normal",
  "dCodSeg": "572819034",
  "iTiDE": 1,
  "dDesTiDE": "Factura electrónica",
  "dNumTim": 77441122,
  "dEst": "003",
  "dPunExp": "004",
  "dNumDoc": "0000789",
  "dFeIniT": "2024-07-15",
  "dFeEmiDE": "2025-09-27T16:45:10",
  "iTipTra": 2,
  "dDesTipTra": "Prestación de servicios",
  "iTImp": 1,
  "dDesTImp": "IVA",
  "cMoneOpe": "PYG",
  "dDesMoneOpe": "Guarani",
  "dRucEm": 4839205,
  "dDVEmi": 4,
  "iTipCont": 2,
  "dNomEmi": "SERVITEC S.R.L.",
  "dNomFanEmi": "SERVITEC EXPRESS",
  "dDirEmi": "AV. CENTRAL KM 5",
  "dNumCas": 150,
  "cDepEmi": 11,
  "dDesDepEmi": "CENTRAL",
  "cDisEmi": 102,
  "dDesDisEmi": "LUQUE",
  "cCiuEmi": 5500,
  "dDesCiuEmi": "ZONA NORTE",
  "dTelEmi": "0982123456",
  "dEmailE": "info@servitec.com.py",
  "cActEco": 70200,
  "dDesActEco": "SERVICIOS DE REPARACIÓN Y MANTENIMIENTO",
  "iNatRec": 2,
  "iTiOpe": 1,
  "cPaisRec": "PRY",
  "dDesPaisRe": "Paraguay",
  "iTipIDRec": "1",
  "dDTipIDRec": "Cédula paraguaya",
  "dNumIDRec": "45678901",
  "dNomRec": "COMERCIAL EL PUENTE S.A.",
  "iIndPres": 1,
  "dDesIndPres": "Operación presencial",
  "iCondOpe": 2,
  "dDCondOpe": "Crédito",
  "iTiPago": 2,
  "dDesTiPag": "Transferencia bancaria",
  "dMonTiPag": "250000.0",
  "cMoneTiPag": "PYG",
  "dDMoneTiPag": "Guarani",
  "dSubExe": "0.0",
  "dSubExo": "0.0",
  "dSub5": "0.0",
  "dSub10": "250000.0",
  "dTotOpe": "250000.0",
  "dTotDesc": 0,
  "dTotDescGlotem": 0,
  "dTotAntItem": 0,
  "dTotAnt": 0,
  "dPorcDescTotal": 0,
  "dDescTotal": 0,
  "dAnticipo": 0,
  "dRedon": 0,
  "dTotGralOpe": "250000.0",
  "dIVA5": 0,
  "dIVA10": 22727,
  "dLiqTotIVA5": 0,
  "dLiqTotIVA10": 0,
  "dTotIVA": 22727,
  "dBaseGrav5": 0,
  "dBaseGrav10": 227273,
  "dTBasGraIVA": 227273,
  "Detalles": [
    {
      "dCodInt": "A-991",
      "dDesProSer": "SERVICIO DE CONSULTORÍA TÉCNICA",
      "cUniMed": "77",
      "dDesUniMed": "UNI",
      "dCantProSer": "1.0",
      "dPUniProSer": "250000.0",
      "dTotBruOpeItem": "250000.0",
      "dTotOpeItem": "250000.0",
      "iAfecIVA": "1",
      "dDesAfecIVA": "Gravado IVA",
      "dPropIVA": "100",
      "dTasaIVA": "10",
      "dBasGravIVA": "227273",
      "dLiqIVAItem": "22727",
      "dBasExe": "0"
    }
  ]
}
"""
                            )
                    )
            )
            String json
    ) {
        try {

            JsonObject jsonObject = Json.createReader(new StringReader(json))
                    .readObject();
            JsonObject jsonResponse = recibirFacturaUseCase.execute(jsonObject);

            return Response
                    .status(Response.Status.OK)
                    .entity(jsonResponse)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();
        }
    }
}
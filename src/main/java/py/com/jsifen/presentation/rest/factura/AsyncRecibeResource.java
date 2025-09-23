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
  "iTipEmi": 2,
  "dDesTipEmi": "Contingencia",
  "dCodSeg": "853920147",
  "iTiDE": 1,
  "dDesTiDE": "Factura electrónica",
  "dNumTim": "99887755",
  "dEst": "002",
  "dPunExp": "005",
  "dNumDoc": "0000456",
  "dFeIniT": "2024-05-10",
  "dFeEmiDE": "2024-11-15T14:25:33",
  "iTipTra": "2",
  "dDesTipTra": "Prestación de servicios",
  "iTImp": "1",
  "dDesTImp": "IVA",
  "cMoneOpe": "PYG",
  "dDesMoneOpe": "Guarani",
  "dRucEm": "9081723",
  "dDVEmi": "3",
  "iTipCont": "2",
  "dNomEmi": "TECNOFUTURO S.A.",
  "dDirEmi": "AV. SOL NACIENTE 1200 C/ AURORA",
  "dNumCas": "1200",
  "cDepEmi": "07",
  "dDesDepEmi": "ALTO PARANÁ",
  "cDisEmi": "075",
  "dDesDisEmi": "CIUDAD DEL ESTE",
  "cCiuEmi": "4120",
  "dDesCiuEmi": "KM 7",
  "dTelEmi": "0971123456",
  "dEmailE": "contacto@tecnofuturo.com",
  "cActEco": "62010",
  "dDesActEco": "DESARROLLO DE SOFTWARE A MEDIDA",
  "iNatRec": "1",
  "iTiOpe": "1",
  "cPaisRec": "PRY",
  "dDesPaisRe": "Paraguay",
  "iTipIDRec": "1",
  "dDTipIDRec": "Cédula paraguaya",
  "dNumIDRec": 90123456,
  "dNomRec": "INVERSIONES DEL SOL S.R.L.",
  "dRucRec": "90123456",
  "dDVRec": 2,
  "iIndPres": 1,
  "dDesIndPres": "Operación presencial",
  "iCondOpe": "2",
  "dDCondOpe": "Crédito",
  "iTiPago": 2,
  "dDesTiPag": "Transferencia bancaria",
  "dMonTiPag": 350000,
  "cMoneTiPag": "PYG",
  "dDMoneTiPag": "Guarani",
  "Detalles": [
    {
      "dCodInt": "S-2.3-4501",
      "dDesProSer": "SERVICIO DE MANTENIMIENTO DE SISTEMAS",
      "cUniMed": 99,
      "dDesUniMed": "servicio",
      "dCantProSer": 1,
      "dPUniProSer": 350000,
      "dTotBruOpeItem": 350000,
      "dTotOpeItem": 350000,
      "iAfecIVA": 1,
      "dDesAfecIVA": "Gravado IVA",
      "dPropIVA": 100,
      "dTasaIVA": 10,
      "dBasGravIVA": 318181,
      "dLiqIVAItem": 31818,
      "dBasExe": 0
    }
  ],
  "dTotOpe": 350000,
  "dTotIVA": 31818
}
"""
                            )
                    )
            )
            String json
    ) {
        try {
            JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
            // Procesar la factura con el caso de uso


            JsonObject jsonResponse = recibirFacturaUseCase.execute(jsonObject);


            //JsonObject resultado = procesarFacturaUseCase.execute(factura);

            //return Response.status(Response.Status.OK).entity(resultado).build();
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();
        }
    }
}
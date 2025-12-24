package com.jsifen.presentacion.rest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Test;
import com.jsifen.application.usecase.factura.RecibirFacturaUseCase;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@QuarkusTest
public class AsyncRecibeResourceTest {

    private static final String FACTURA_JSON = """        
    {
        "iTipEmi":1,
            "dDesTipEmi":"Normal",
            "dCodSeg":"106381931",
            "iTiDE":1,
            "dDesTiDE":"Factura electrónica",
            "dNumTim":16693341,
            "dEst":"001",
            "dPunExp":"002",
            "dNumDoc":"0000090",
            "dFeIniT":"2023-09-25",
            "dFeEmiDE":"2025-09-26T09:01:28",
            "iTipTra":1,
            "dDesTipTra":"Venta de mercadería",
            "iTImp":1,
            "dDesTImp":"IVA",
            "cMoneOpe":"PYG",
            "dDesMoneOpe":"Guarani",
            "dRucEm":3694014,
            "dDVEmi":3,
            "iTipCont":1,
            "dNomEmi":"ANTONIO AQUINO PEREZ",
            "dNomFanEmi":"DEPOSITO SAN ANTONIO",
            "dDirEmi":"14 DE MAYO E/ CARMELO PERALTA",
            "dNumCas":0,
            "cDepEmi":6,
            "dDesDepEmi":"CAAGUAZU",
            "cDisEmi":61,
            "dDesDisEmi":"CNEL. OVIEDO",
            "cCiuEmi":2886,
            "dDesCiuEmi":"CNEL. OVIEDO",
            "dTelEmi":"0975493691",
            "dEmailE":"atoniocomer@gmail.com",
            "cActEco":47190,
            "dDesActEco":"COMERCIO AL POR MENOR DE OTROS PRODUCTOS EN COMERCIOS NO ESPECIALIZADOS",
            "iNatRec":2,
            "iTiOpe":2,
            "cPaisRec":"PRY",
            "dDesPaisRe":"Paraguay",
            "iTipIDRec":"1",
            "dDTipIDRec":"Cédula paraguaya",
            "dNumIDRec":"0",
            "dNomRec":"Sin Nombre",
            "iIndPres":1,
            "dDesIndPres":"Operación presencial",
            "iCondOpe":1,
            "dDCondOpe":"Contado",
            "iTiPago":1,
            "dDesTiPag":"Efectivo",
            "dMonTiPag":"1000.0",
            "cMoneTiPag":"PYG",
            "dDMoneTiPag":"Guarani",
            "dSubExe":"0.0",
            "dSubExo":"0.0",
            "dSub5":"0.0",
            "dSub10":"1000.0",
            "dTotOpe":"1000.0",
            "dTotDesc":0,
            "dTotDescGlotem":0,
            "dTotAntItem":0,
            "dTotAnt":0,
            "dPorcDescTotal":0,
            "dDescTotal":0,
            "dAnticipo":0,
            "dRedon":0,
            "dTotGralOpe":"1000.0",
            "dIVA5":0,
            "dIVA10":91,
            "dLiqTotIVA5":0,
            "dLiqTotIVA10":0,
            "dTotIVA":91,
            "dBaseGrav5":0,
            "dBaseGrav10":909,
            "dTBasGraIVA":909,
            "Detalles":[
        {
            "dCodInt":"6016",
                "dDesProSer":"MERCADERIAS VARIOS 10%",
                "cUniMed":"77",
                "dDesUniMed":"UNI",
                "dCantProSer":"1.0",
                "dPUniProSer":"1000.0",
                "dTotBruOpeItem":"1000.0",
                "dTotOpeItem":"1000.0",
                "iAfecIVA":"1",
                "dDesAfecIVA":"Gravado IVA",
                "dPropIVA":"100",
                "dTasaIVA":"10",
                "dBasGravIVA":"909",
                "dLiqIVAItem":"91",
                "dBasExe":"0"
        }
          ]
    }
        """;


    @Inject
    RecibirFacturaUseCase recibirFacturaUseCase;


    @Test
    void testdeintegracion() throws Exception {
        JsonObject jsonObject = Json.createReader(new StringReader(FACTURA_JSON))
                .readObject();
        JsonObject jsonResponse = recibirFacturaUseCase.execute(jsonObject);

        System.out.println(jsonResponse );


    }





}
package py.com.jsifen.domain.de.structure;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import py.com.jsifen.domain.de.gen.DeComplemento;
import py.com.jsifen.domain.de.mapping.Serializacion;

import java.io.StringReader;

public class DeXmlStructure {

    public DeXmlElement root ;


    public void generar_esquema (Serializacion serializacion,
                                 DeComplemento complemento){


        // si se puede pòner aca el
        // json del campo
        /*
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        JsonObject jsonobject = new JsonObject();
        jsonobject = gson.fromJson(serializacion.getJson(), JsonObject.class);

         */


        JsonReader reader = Json.createReader(
                new StringReader(serializacion.getJson())
        );
        JsonObject jsonobject = reader.readObject();
        //reader.close();      // cerrar explícitamente



        DeXmlElement rDE = new DeXmlElement("rDE");

        rDE.getHijos().add(new DeXmlElement("dVerFor", serializacion, null ) );

        DeXmlElement DE = new DeXmlElement("DE");
        DE.getAtributos().add(new XmlAttribute("Id",
                complemento.getId()));
        rDE.getHijos().add(DE);


        DE.getHijos().add(new DeXmlElement("dDVId", serializacion, null ) );
        DE.getHijos().add(new DeXmlElement("dFecFirma" , serializacion, null));
        DE.getHijos().add(new DeXmlElement("dSisFact" , serializacion, null ));

        DeXmlElement gOpeDE = new DeXmlElement("gOpeDE");
        DE.getHijos().add(gOpeDE);

        gOpeDE.hijos_add( serializacion, "iTipEmi", jsonobject );
        gOpeDE.hijos_add( serializacion, "dDesTipEmi", jsonobject );
        gOpeDE.hijos_add( serializacion, "dCodSeg", jsonobject );

        gOpeDE.hijos_add( serializacion, "dInfoEmi", jsonobject );
        gOpeDE.hijos_add( serializacion, "dInfoFisc", jsonobject );


        DeXmlElement gTimb = new DeXmlElement("gTimb");
        DE.getHijos().add(gTimb);


        gTimb.getHijos().add(new DeXmlElement("iTiDE", serializacion,  null ));
        gTimb.getHijos().add(new DeXmlElement("dDesTiDE", serializacion,  null ));
        gTimb.getHijos().add(new DeXmlElement("dNumTim", serializacion,  null ));
        gTimb.getHijos().add(new DeXmlElement("dEst", serializacion,  null ));
        gTimb.getHijos().add(new DeXmlElement("dPunExp", serializacion,  null ));
        gTimb.getHijos().add(new DeXmlElement("dNumDoc", serializacion,  null ));

        gTimb.hijos_add( serializacion, "dSerieNum", jsonobject );
        gTimb.hijos_add( serializacion, "dFeIniT", jsonobject );

        DeXmlElement gDatGralOpe = new DeXmlElement("gDatGralOpe");
        DE.getHijos().add(gDatGralOpe);

        gDatGralOpe.getHijos().add(new DeXmlElement("dFeEmiDE", serializacion,  null  ));

        DeXmlElement gOpeCom = new DeXmlElement("gOpeCom");
        gDatGralOpe.getHijos().add(gOpeCom);

        gOpeCom.hijos_add( serializacion, "iTipTra", jsonobject );
        gOpeCom.hijos_add( serializacion, "dDesTipTra", jsonobject );
        gOpeCom.hijos_add( serializacion, "iTImp", jsonobject );
        gOpeCom.hijos_add( serializacion, "dDesTImp", jsonobject );
        gOpeCom.hijos_add( serializacion, "cMoneOpe", jsonobject );
        gOpeCom.hijos_add( serializacion, "dDesMoneOpe", jsonobject );

        gOpeCom.hijos_add( serializacion, "dCondTiCam", jsonobject );
        gOpeCom.hijos_add( serializacion, "dTiCam", jsonobject );

        gOpeCom.hijos_add( serializacion, "iCondAnt", jsonobject );
        gOpeCom.hijos_add( serializacion, "dDesCondAnt", jsonobject );


        DeXmlElement gEmis = new DeXmlElement("gEmis");
        gDatGralOpe.getHijos().add(gEmis);

        gEmis.getHijos().add(new DeXmlElement("dRucEm", serializacion,  null ));
        gEmis.getHijos().add(new DeXmlElement("dDVEmi", serializacion,  null ));
        gEmis.getHijos().add(new DeXmlElement("iTipCont", serializacion,  null ));

        gEmis.hijos_add( serializacion, "cTipReg", jsonobject );

        gEmis.getHijos().add(new DeXmlElement("dNomEmi", serializacion,  null ));

        gEmis.hijos_add( serializacion, "dNomFanEmi", jsonobject );

        gEmis.getHijos().add(new DeXmlElement("dDirEmi", serializacion,  null ));
        gEmis.getHijos().add(new DeXmlElement("dNumCas", serializacion,  null ));
        gEmis.getHijos().add(new DeXmlElement("cDepEmi", serializacion,  null ));
        gEmis.getHijos().add(new DeXmlElement("dDesDepEmi", serializacion,  null ));


        gEmis.hijos_add( serializacion, "cDisEmi", jsonobject );
        gEmis.hijos_add( serializacion, "dDesDisEmi", jsonobject );



        gEmis.getHijos().add(new DeXmlElement("cCiuEmi", serializacion,  null ));
        gEmis.getHijos().add(new DeXmlElement("dDesCiuEmi", serializacion,  null ));
        gEmis.getHijos().add(new DeXmlElement("dTelEmi", serializacion,  null ));
        gEmis.getHijos().add(new DeXmlElement("dEmailE", serializacion,  null ));

        DeXmlElement gActEco = new DeXmlElement("gActEco");
        gEmis.getHijos().add(gActEco);

        gActEco.getHijos().add(new DeXmlElement("cActEco", serializacion,  null ));
        gActEco.getHijos().add(new DeXmlElement("dDesActEco", serializacion,  null ));


        DeXmlElement gDatRec = new DeXmlElement("gDatRec");
        gDatGralOpe.getHijos().add(gDatRec);


        gDatRec.getHijos().add(new DeXmlElement("iNatRec", serializacion,  null ));
        gDatRec.getHijos().add(new DeXmlElement("iTiOpe", serializacion,  null ));
        gDatRec.getHijos().add(new DeXmlElement("cPaisRec", serializacion,  null ));
        gDatRec.getHijos().add(new DeXmlElement("dDesPaisRe", serializacion,  null ));


        gDatRec.hijos_add( serializacion, "iTiContRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dRucRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dDVRec", jsonobject );
        gDatRec.hijos_add( serializacion, "iTipIDRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dDTipIDRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dNumIDRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dNomRec", jsonobject );

        gDatRec.hijos_add( serializacion, "dDirRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dNumCasRec", jsonobject );
        gDatRec.hijos_add( serializacion, "cDepRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dDesDepRec", jsonobject );
        gDatRec.hijos_add( serializacion, "cDisRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dDesDisRec", jsonobject );
        gDatRec.hijos_add( serializacion, "cCiuRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dDesCiuRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dTelRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dCelRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dEmailRec", jsonobject );
        gDatRec.hijos_add( serializacion, "dCodCliente", jsonobject );


        DeXmlElement gDtipDE = new DeXmlElement("gDtipDE");
        DE.getHijos().add(gDtipDE);

        DeXmlElement gCamFE = new DeXmlElement("gCamFE");
        gDtipDE.getHijos().add(gCamFE);

        gCamFE.hijos_add( serializacion, "iIndPres", jsonobject );
        gCamFE.hijos_add( serializacion, "dDesIndPres", jsonobject );
        gCamFE.hijos_add( serializacion, "dFecEmNR", jsonobject );


        DeXmlElement gCompPub = new DeXmlElement("gCompPub");
        gCamFE.getHijos().add(gCompPub);

        gCompPub.hijos_add( serializacion, "dModCont", jsonobject );
        gCompPub.hijos_add( serializacion, "dEntCont", jsonobject );
        gCompPub.hijos_add( serializacion, "dAnoCont", jsonobject );
        gCompPub.hijos_add( serializacion, "dSecCont", jsonobject );
        gCompPub.hijos_add( serializacion, "dFeCodCont", jsonobject );


        //  Campos que componen la Autofactura Electrónica AFE (E300-E399)
        DeXmlElement gCamAE = new DeXmlElement("gCamAE");
        gDtipDE.getHijos().add(gCamAE);

        gCamAE.hijos_add( serializacion, "iNatVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dDesNatVen", jsonobject );
        gCamAE.hijos_add( serializacion, "iTipIDVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dDTipIDVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dNumIDVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dNomVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dDirVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dNumCasVen", jsonobject );
        gCamAE.hijos_add( serializacion, "cDepVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dDesDepVen", jsonobject );
        gCamAE.hijos_add( serializacion, "cDisVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dDesDisVen", jsonobject );
        gCamAE.hijos_add( serializacion, "cCiuVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dDesCiuVen", jsonobject );
        gCamAE.hijos_add( serializacion, "dDirProv", jsonobject );
        gCamAE.hijos_add( serializacion, "cDepProv", jsonobject );
        gCamAE.hijos_add( serializacion, "dDesDepProv", jsonobject );
        gCamAE.hijos_add( serializacion, "cDisProv", jsonobject );
        gCamAE.hijos_add( serializacion, "dDesDisProv", jsonobject );
        gCamAE.hijos_add( serializacion, "cCiuProv", jsonobject );
        gCamAE.hijos_add( serializacion, "dDesCiuProv", jsonobject );


        //  Campos que componen la Nota de Crédito/Débito Electrónica NCE-NDE (E400-E499)
        DeXmlElement gCamNCDE = new DeXmlElement("gCamNCDE");
        gDtipDE.getHijos().add(gCamNCDE);

        gCamNCDE.hijos_add( serializacion, "iMotEmi", jsonobject );
        gCamNCDE.hijos_add( serializacion, "dDesMotEmi", jsonobject );



        //  E6. Campos que componen la Nota de Remisión Electrónica (E500-E599)
        DeXmlElement gCamNRE = new DeXmlElement("gCamNRE");
        gDtipDE.getHijos().add(gCamNRE);

        gCamNRE.hijos_add( serializacion, "iMotEmiNR", jsonobject );
        gCamNRE.hijos_add( serializacion, "dDesMotEmiNR", jsonobject );
        gCamNRE.hijos_add( serializacion, "iRespEmiNR", jsonobject );
        gCamNRE.hijos_add( serializacion, "dDesRespEmiNR", jsonobject );
        gCamNRE.hijos_add( serializacion, "dKmR", jsonobject );
        gCamNRE.hijos_add( serializacion, "dFecEm", jsonobject );


        //  E7. Campos que describen la condición de la operación (E600-E699)

        DeXmlElement gCamCond = new DeXmlElement("gCamCond");
        gDtipDE.getHijos().add(gCamCond);

        gCamCond.hijos_add( serializacion, "iCondOpe", jsonobject );
        gCamCond.hijos_add( serializacion, "dDCondOpe", jsonobject );


        DeXmlElement gPaConEIni = new DeXmlElement("gPaConEIni");
        gCamCond.getHijos().add(gPaConEIni);

        gPaConEIni.hijos_add( serializacion, "iTiPago", jsonobject );
        gPaConEIni.hijos_add( serializacion, "dDesTiPag", jsonobject );
        gPaConEIni.hijos_add( serializacion, "dMonTiPag", jsonobject );
        gPaConEIni.hijos_add( serializacion, "cMoneTiPag", jsonobject );
        gPaConEIni.hijos_add( serializacion, "dDMoneTiPag", jsonobject );
        gPaConEIni.hijos_add( serializacion, "dTiCamTiPag", jsonobject );


        DeXmlElement gPagTarCD = new DeXmlElement("gPagTarCD");
        gPaConEIni.getHijos().add(gPagTarCD);
        gPagTarCD.hijos_add( serializacion, "iDenTarj", jsonobject );
        gPagTarCD.hijos_add( serializacion, "dDesDenTarj", jsonobject );
        gPagTarCD.hijos_add( serializacion, "dRSProTar", jsonobject );
        gPagTarCD.hijos_add( serializacion, "iForProPa", jsonobject );
        gPagTarCD.hijos_add( serializacion, "dCodAuOpe", jsonobject );
        gPagTarCD.hijos_add( serializacion, "dNomTit", jsonobject );
        gPagTarCD.hijos_add( serializacion, "dNumTarj", jsonobject );


        DeXmlElement gPagCheq = new DeXmlElement("gPagCheq");
        gPaConEIni.getHijos().add(gPagCheq);
        gPagCheq.hijos_add( serializacion, "dNumCheq", jsonobject );
        gPagCheq.hijos_add( serializacion, "dBcoEmi", jsonobject );


        //  E7.2. Campos que describen la operación a crédito (E640-E649)
        DeXmlElement gPagCred = new DeXmlElement("gPagCred");
        gCamCond.getHijos().add(gPagCred);

        gPagCred.hijos_add( serializacion, "iCondCred", jsonobject );
        gPagCred.hijos_add( serializacion, "dDCondCred", jsonobject );
        gPagCred.hijos_add( serializacion, "dPlazoCre", jsonobject );
        gPagCred.hijos_add( serializacion, "dCuotas", jsonobject );
        gPagCred.hijos_add( serializacion, "dMonEnt", jsonobject );



        //  E7.2.1. Campos que describen las cuotas (E650-E659)
        DeXmlElement gCuotas = new DeXmlElement("gCuotas");
        gPagCred.getHijos().add(gCuotas);
        gCuotas.hijos_add( serializacion, "cMoneCuo", jsonobject );
        gCuotas.hijos_add( serializacion, "dDMoneCuo", jsonobject );
        gCuotas.hijos_add( serializacion, "dMonCuota", jsonobject );
        gCuotas.hijos_add( serializacion, "dVencCuo", jsonobject );


        JsonArray arrayDetalles = jsonobject.getJsonArray("Detalles");
        int cantdet = arrayDetalles.size();



        for (int i = 0; i < cantdet ; i++) {

            //JsonObject jsonObject = arrayDetalles.get(i).getAsJsonObject();
            JsonObject jsonObject = arrayDetalles.getJsonObject(i);

            DeXmlElement gCamItem = new DeXmlElement("gCamItem");
            gDtipDE.getHijos().add(gCamItem);


            gCamItem.getHijos().add(new DeXmlElement("dCodInt", jsonObject));

            gCamItem.getHijos().add(new DeXmlElement("dDesProSer", jsonObject));
            gCamItem.getHijos().add(new DeXmlElement("cUniMed", jsonObject));
            gCamItem.getHijos().add(new DeXmlElement("dDesUniMed", jsonObject));
            gCamItem.getHijos().add(new DeXmlElement("dCantProSer", jsonObject));


            DeXmlElement gValorItem = new DeXmlElement("gValorItem");
            gCamItem.getHijos().add(gValorItem);

            gValorItem.getHijos().add(new DeXmlElement("dPUniProSer", jsonObject));
            gValorItem.getHijos().add(new DeXmlElement("dTiCamIt", jsonObject));
            gValorItem.getHijos().add(new DeXmlElement("dTotBruOpeItem", jsonObject));

            DeXmlElement gValorRestaItem = new DeXmlElement("gValorRestaItem");
            gValorItem.getHijos().add(gValorRestaItem);

            //gValorRestaItem.getHijos().add(new DeXmlElement(, jsonObject));
            gValorRestaItem.hijos_add("dDescItem", jsonObject);
            gValorRestaItem.hijos_add("dPorcDesIt", jsonObject);
            gValorRestaItem.hijos_add("dDescGloItem", jsonObject);
            gValorRestaItem.hijos_add("dAntPreUniIt", jsonObject);
            gValorRestaItem.hijos_add("dTotOpeItem", jsonObject);
            gValorRestaItem.hijos_add("dTotOpeGs", jsonObject);


            DeXmlElement gCamIVA = new DeXmlElement("gCamIVA");
            gCamItem.getHijos().add(gCamIVA);

            gCamIVA.getHijos().add(new DeXmlElement("iAfecIVA", jsonObject));
            gCamIVA.getHijos().add(new DeXmlElement("dDesAfecIVA" , jsonObject));
            gCamIVA.getHijos().add(new DeXmlElement("dPropIVA" , jsonObject));
            gCamIVA.getHijos().add(new DeXmlElement("dTasaIVA" , jsonObject));

            gCamIVA.getHijos().add(new DeXmlElement("dBasGravIVA" , jsonObject));
            gCamIVA.getHijos().add(new DeXmlElement("dLiqIVAItem" , jsonObject));
            gCamIVA.getHijos().add(new DeXmlElement("dBasExe" , jsonObject));


        }
        // fin detelles



        DeXmlElement gCamEsp = new DeXmlElement("gCamEsp");
        gDtipDE.getHijos().add(gCamEsp);

        // E9.2. Sector Energía Eléctrica (E791-E799)
        DeXmlElement gGrupEner = new DeXmlElement("gGrupEner");
        gCamEsp.getHijos().add(gGrupEner);
        gGrupEner.hijos_add( serializacion, "dNroMed", jsonobject );
        gGrupEner.hijos_add( serializacion, "dActiv", jsonobject );
        gGrupEner.hijos_add( serializacion, "dCateg", jsonobject );
        gGrupEner.hijos_add( serializacion, "dLecAnt", jsonobject );
        gGrupEner.hijos_add( serializacion, "dLecAct", jsonobject );
        gGrupEner.hijos_add( serializacion, "dConKwh", jsonobject );


        // E9.4. Sector de Supermercados (E810-E819)
        DeXmlElement gGrupSup = new DeXmlElement("gGrupSup");
        gCamEsp.getHijos().add(gGrupSup);
        gGrupSup.hijos_add( serializacion, "dNomCaj", jsonobject );
        gGrupSup.hijos_add( serializacion, "dEfectivo", jsonobject );
        gGrupSup.hijos_add( serializacion, "dVuelto", jsonobject );
        gGrupSup.hijos_add( serializacion, "dDonac", jsonobject );
        gGrupSup.hijos_add( serializacion, "dDesDonac", jsonobject );


        //  E9.5. Grupo de datos adicionales de uso comercial (E820-E829)
        DeXmlElement gGrupAdi = new DeXmlElement("gGrupAdi");
        gCamEsp.getHijos().add(gGrupAdi);

        gGrupAdi.hijos_add( serializacion, "dCiclo", jsonobject );
        gGrupAdi.hijos_add( serializacion, "dFecIniC", jsonobject );
        gGrupAdi.hijos_add( serializacion, "dFecFinC", jsonobject );
        gGrupAdi.hijos_add( serializacion, "dVencPag", jsonobject );
        gGrupAdi.hijos_add( serializacion, "dContrato", jsonobject );
        gGrupAdi.hijos_add( serializacion, "dSalAnt", jsonobject );


        //  E10. Campos que describen el transporte de las mercaderías (E900-E999)
        DeXmlElement gTransp = new DeXmlElement("gTransp");
        gDtipDE.getHijos().add(gTransp);


        gTransp.hijos_add( serializacion, "iTipTrans", jsonobject );
        gTransp.hijos_add( serializacion, "dDesTipTrans", jsonobject );
        gTransp.hijos_add( serializacion, "iModTrans", jsonobject );
        gTransp.hijos_add( serializacion, "dDesModTrans", jsonobject );
        gTransp.hijos_add( serializacion, "iRespFlete", jsonobject );
        gTransp.hijos_add( serializacion, "cCondNeg", jsonobject );
        gTransp.hijos_add( serializacion, "dNuManif", jsonobject );
        gTransp.hijos_add( serializacion, "dNuManif", jsonobject );
        gTransp.hijos_add( serializacion, "dNuDespImp", jsonobject );
        gTransp.hijos_add( serializacion, "dNuDespImp", jsonobject );
        gTransp.hijos_add( serializacion, "dIniTras", jsonobject );
        gTransp.hijos_add( serializacion, "dFinTras", jsonobject );
        gTransp.hijos_add( serializacion, "cPaisDest", jsonobject );
        gTransp.hijos_add( serializacion, "dDesPaisDest", jsonobject );


        // E10.1. Campos que identifican el local de salida de las mercaderías (E920-E939)
        DeXmlElement gCamSal = new DeXmlElement("gCamSal");
        gTransp.getHijos().add(gCamSal);

        gCamSal.hijos_add( serializacion, "dDirLocSal", jsonobject );
        gCamSal.hijos_add( serializacion, "dNumCasSal", jsonobject );
        gCamSal.hijos_add( serializacion, "dComp1Sal", jsonobject );
        gCamSal.hijos_add( serializacion, "dComp2Sal", jsonobject );
        gCamSal.hijos_add( serializacion, "cDepSal", jsonobject );
        gCamSal.hijos_add( serializacion, "dDesDepSal", jsonobject );
        gCamSal.hijos_add( serializacion, "cDisSal", jsonobject );
        gCamSal.hijos_add( serializacion, "dDesDisSal", jsonobject );
        gCamSal.hijos_add( serializacion, "cCiuSal", jsonobject );
        gCamSal.hijos_add( serializacion, "dDesCiuSal", jsonobject );
        gCamSal.hijos_add( serializacion, "dTelSal", jsonobject );



        // E10.2. Campos que identifican el local de entrega de las mercaderías (E940-E959)
        DeXmlElement gCamEnt = new DeXmlElement("gCamEnt");
        gTransp.getHijos().add(gCamEnt);

        gCamEnt.hijos_add( serializacion, "dDirLocEnt", jsonobject );
        gCamEnt.hijos_add( serializacion, "dNumCasEnt", jsonobject );
        gCamEnt.hijos_add( serializacion, "dComp1Ent", jsonobject );
        gCamEnt.hijos_add( serializacion, "dComp2Ent", jsonobject );
        gCamEnt.hijos_add( serializacion, "cDepEnt", jsonobject );
        gCamEnt.hijos_add( serializacion, "dDesDepEnt", jsonobject );
        gCamEnt.hijos_add( serializacion, "cDisEnt", jsonobject );
        gCamEnt.hijos_add( serializacion, "dDesDisEnt", jsonobject );
        gCamEnt.hijos_add( serializacion, "cCiuEnt", jsonobject );
        gCamEnt.hijos_add( serializacion, "dDesCiuEnt", jsonobject );
        gCamEnt.hijos_add( serializacion, "dTelEnt", jsonobject );


        // E10.3. Campos que identifican el vehículo de traslado de mercaderías (E960-E979)
        DeXmlElement gVehTras = new DeXmlElement("gVehTras");
        gTransp.getHijos().add(gVehTras);

        gVehTras.hijos_add( serializacion, "dTiVehTras", jsonobject );
        gVehTras.hijos_add( serializacion, "dMarVeh", jsonobject );
        gVehTras.hijos_add( serializacion, "dTipIdenVeh", jsonobject );
        gVehTras.hijos_add( serializacion, "dNroIDVeh", jsonobject );
        gVehTras.hijos_add( serializacion, "dAdicVeh", jsonobject );
        gVehTras.hijos_add( serializacion, "dNroMatVeh", jsonobject );
        gVehTras.hijos_add( serializacion, "dNroVuelo", jsonobject );

        // E10.4. Campos que identifican al transportista (persona física o jurídica) (E980-E999)
        DeXmlElement gCamTrans = new DeXmlElement("gCamTrans");
        gTransp.getHijos().add(gCamTrans);

        gCamTrans.hijos_add( serializacion, "iNatTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "dNomTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "dRucTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "dDVTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "iTipIDTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "dDTipIDTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "dNumIDTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "cNacTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "dDesNacTrans", jsonobject );
        gCamTrans.hijos_add( serializacion, "dNumIDChof", jsonobject );
        gCamTrans.hijos_add( serializacion, "dNomChof", jsonobject );
        gCamTrans.hijos_add( serializacion, "dDomFisc", jsonobject );
        gCamTrans.hijos_add( serializacion, "dDirChof", jsonobject );
        gCamTrans.hijos_add( serializacion, "dNombAg", jsonobject );
        gCamTrans.hijos_add( serializacion, "dRucAg", jsonobject );
        gCamTrans.hijos_add( serializacion, "dDVAg", jsonobject );
        gCamTrans.hijos_add( serializacion, "dDirAge", jsonobject );



        DeXmlElement gTotSub = new DeXmlElement("gTotSub");
        DE.getHijos().add(gTotSub);

        gTotSub.getHijos().add(new DeXmlElement("dSubExe", serializacion,  null ));
        gTotSub.hijos_add( serializacion, "dSubExo", jsonobject );
        gTotSub.hijos_add( serializacion, "dSub5", jsonobject );


        gTotSub.getHijos().add(new DeXmlElement("dSub10", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dTotOpe", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dTotDesc", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dTotDescGlotem", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dTotAntItem", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dTotAnt", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dPorcDescTotal", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dDescTotal", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dAnticipo", serializacion,  null));

        gTotSub.getHijos().add(new DeXmlElement("dRedon", serializacion,  null));

        gTotSub.hijos_add( serializacion, "dComi", jsonobject );


        gTotSub.getHijos().add(new DeXmlElement("dTotGralOpe", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dIVA5", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dIVA10", serializacion,  null));

        gTotSub.hijos_add( serializacion, "dLiqTotIVA5", jsonobject );
        gTotSub.hijos_add( serializacion, "dLiqTotIVA10", jsonobject );


        gTotSub.hijos_add( serializacion, "dIVAComi", jsonobject );

        gTotSub.getHijos().add(new DeXmlElement("dTotIVA", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dBaseGrav5", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dBaseGrav10", serializacion,  null));
        gTotSub.getHijos().add(new DeXmlElement("dTBasGraIVA", serializacion,  null));

        gTotSub.hijos_add( serializacion, "dTotalGs", jsonobject );




        DeXmlElement gCamDEAsoc = new DeXmlElement("gCamDEAsoc");
        DE.getHijos().add(gCamDEAsoc);

        gCamDEAsoc.hijos_add( serializacion, "iTipDocAso", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dDesTipDocAso", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dCdCDERef", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dNTimDI", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dEstDocAso", jsonobject );

        gCamDEAsoc.hijos_add( serializacion, "dPExpDocAso", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dNumDocAso", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "iTipoDocAso", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dDTipoDocAso", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dFecEmiDI", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dNumComRet", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dNumResCF", jsonobject );

        gCamDEAsoc.hijos_add( serializacion, "iTipCons", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dDesTipCons", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dNumCons", jsonobject );
        gCamDEAsoc.hijos_add( serializacion, "dNumControl", jsonobject );


        this.root = rDE;

    }

}

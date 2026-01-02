package com.jsifen.infrastructure.sifen.xml.gen;

import com.jsifen.infrastructure.sifen.xml.mapping.Serializacion;
import com.jsifen.domain.service.SifenDvCalculator;
import com.jsifen.infrastructure.util.xml.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class CdcGenerator {

    public static String generarCdc (String json) throws Exception {

        Serializacion serializacion = new Serializacion();
        serializacion.setJson(json);

        String iTiDE   = serializacion.getValorJson("iTiDE");
        String dRucEm  = serializacion.getValorJson("dRucEm");
        String dDVEmi  = serializacion.getValorJson("dDVEmi");
        String dEst    = serializacion.getValorJson("dEst");
        String dPunExp = serializacion.getValorJson("dPunExp");
        String dNumDoc = serializacion.getValorJson("dNumDoc");
        String iTipCont= serializacion.getValorJson("iTipCont");
        String dFeEmiDE= serializacion.getValorJson("dFeEmiDE");
        String iTipEmi = serializacion.getValorJson("iTipEmi");
        String dCodSeg = serializacion.getValorJson("dCodSeg");

        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date fecha = sd1.parse(dFeEmiDE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String cdc = StringUtils.leftPad(iTiDE.trim(), '0', 2)
                + StringUtils.leftPad(dRucEm.trim(), '0', 8)
                + dDVEmi
                + dEst
                + dPunExp
                + StringUtils.leftPad(dNumDoc.trim(), '0', 7)
                + iTipCont
                + sdf.format(fecha)
                + iTipEmi
                + StringUtils.leftPad(dCodSeg.trim(), '0', 9);

        // agrega el dígito verificador
        return cdc + SifenDvCalculator.generateDv(cdc);
    }

}

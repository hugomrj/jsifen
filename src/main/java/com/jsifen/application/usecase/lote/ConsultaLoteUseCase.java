package com.jsifen.application.usecase.lote;

import com.jsifen.presentation.rest.consulta.lote.dto.request.ConsultarLoteRequest;
import com.jsifen.presentation.rest.consulta.lote.dto.response.ConsultaLoteResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.jsifen.domain.repository.LoteRepository;
import jakarta.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaLoteUseCase {

    @Inject
    LoteRepository loteRepository;

    public ConsultaLoteResponse execute(String lote) {

        JsonObject json = loteRepository.buscarPorLote(lote);

        JsonObject envelope = json.getJsonObject("env:Envelope");
        JsonObject body = envelope.getJsonObject("env:Body");
        JsonObject data = body.getJsonObject("ns2:rResEnviConsLoteDe");

        String codigoLote = data.getString("ns2:dCodResLot", null);
        String mensajeLote = data.getString("ns2:dMsgResLot", null);

        List<ConsultaLoteResponse.Resultado> resultados = new ArrayList<>();

        if (data.containsKey("ns2:gResProcLote")) {

            JsonObject loteNode = data.getJsonObject("ns2:gResProcLote");

            String cdc = loteNode.getString("ns2:id", null);
            String estado = loteNode.getString("ns2:dEstRes", null);

            if ("Rechazado".equalsIgnoreCase(estado)
                    && loteNode.containsKey("ns2:gResProc")) {

                JsonObject msg = loteNode.getJsonObject("ns2:gResProc");

                resultados.add(
                        new ConsultaLoteResponse.Resultado(
                                cdc,
                                estado,
                                msg.getInt("ns2:dCodRes", 0),
                                msg.getString("ns2:dMsgRes", null)
                        )
                );
            } else {
                resultados.add(
                        new ConsultaLoteResponse.Resultado(
                                cdc,
                                estado,
                                null,
                                null
                        )
                );
            }
        }

        return new ConsultaLoteResponse(
                codigoLote,
                mensajeLote,
                resultados
        );
    }
}

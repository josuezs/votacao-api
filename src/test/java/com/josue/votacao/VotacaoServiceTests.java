package com.josue.votacao;

import com.josue.votacao.dto.VotacaoDTO;
import com.josue.votacao.dto.VotoDTO;
import com.josue.votacao.model.Pauta;
import com.josue.votacao.service.PautaService;
import com.josue.votacao.service.VotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes nas classes de negócio.
 */
@SpringBootTest
class VotacaoServiceTests {

    public static final Integer VOTO_SIM = 1;
    public static final Integer VOTO_NAO = 0;
    private Logger log = LoggerFactory.getLogger(VotacaoServiceTests.class);
    @Autowired
    private PautaService pServ;

    @Autowired
    private VotoService vServ;

    @Test
    @DisplayName("Caminho feliz: cria uma pauta, abre votação, realiza votos e fecha votação.")
    void testeCompleto() {
        Pauta p = pServ.insert("Assembléia 1");

        pServ.open(p.getId(), null);

        int qtdSim = 0, qtdNao = 0;
        try {
            VotoDTO votoDto = new VotoDTO();
            votoDto.setIdPauta(p.getId());
            votoDto.setCpfAssociado(78873087396l);
            votoDto.setIndVotoSim(VOTO_SIM);
            vServ.insert(votoDto);
            qtdSim++;
        } catch (Exception e) {
            log.info("Voto não contabilizado: " + e.getMessage());
        }

        try {
            VotoDTO votoDto = new VotoDTO();
            votoDto.setIdPauta(p.getId());
            votoDto.setCpfAssociado(93181950017l);
            votoDto.setIndVotoSim(VOTO_SIM);
            vServ.insert(votoDto);
            qtdSim++;
        } catch (Exception e) {
            log.info("Voto não contabilizado: " + e.getMessage());
        }

        try {
            VotoDTO votoDto = new VotoDTO();
            votoDto.setIdPauta(p.getId());
            votoDto.setCpfAssociado(48793075057l);
            votoDto.setIndVotoSim(VOTO_NAO);
            vServ.insert(votoDto);
            qtdNao++;
        } catch (Exception e) {
            log.info("Voto não contabilizado: " + e.getMessage());
        }

        VotacaoDTO vDto = pServ.close(p.getId());
        assertEquals(p.getDesPauta(), vDto.getDesPauta());
        assertEquals(qtdSim, vDto.getQtdSim());
        assertEquals(qtdNao, vDto.getQtdNao());
    }

    @Test
    @DisplayName("Testa voto em pauta inexistente.")
    void testePautaInexistente() {
        Pauta p = pServ.insert("Assembléia 2");
        pServ.open(p.getId(), null);

        VotoDTO votoDto = new VotoDTO();
        votoDto.setIdPauta(p.getId().add(BigInteger.ONE)); // força ID inexistente
        votoDto.setCpfAssociado(78873087396l);
        votoDto.setIndVotoSim(VOTO_SIM);

        assertThrows(IllegalArgumentException.class, () -> {
            vServ.insert(votoDto);
        });
    }

}

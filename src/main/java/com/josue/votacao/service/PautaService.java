package com.josue.votacao.service;

import com.josue.votacao.dto.VotacaoDTO;
import com.josue.votacao.model.Pauta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PautaService {

    private Logger log = LoggerFactory.getLogger(PautaService.class);

    @Autowired
    private PautaRepository pRep;

    @Autowired
    private VotoRepository vRep;

    public Pauta insert(String desPauta) {
        Pauta pauta = pRep.findByDesPautaIgnoreCase(desPauta);
        Assert.isNull(pauta, "Pauta já cadastrada.");
        pauta = new Pauta();
        pauta.setDesPauta(desPauta);
        return pRep.save(pauta);
    }

    public Pauta findById(BigInteger id) {
        Optional<Pauta> pautaOpt = pRep.findById(id);
        Assert.isTrue(pautaOpt.isPresent(), "Pauta não encontrada.");
        return pautaOpt.get();
    }

    @Transactional
    public void open(BigInteger idPauta, Integer qtdMinutos) {
        Optional<Pauta> pautaOpt = pRep.findById(idPauta);
        Assert.isTrue(pautaOpt.isPresent(), "Pauta não encontrada.");
        if (qtdMinutos == null || qtdMinutos < 1) {
            qtdMinutos = 1;
        }
        Pauta pauta = pautaOpt.get();

        // Abre para votação.
        pauta.setDthFimVotacao(LocalDateTime.now().plusMinutes(qtdMinutos));
        pRep.save(pauta);

        // Limpa as votações antigas
        vRep.deleteByIdPauta(pauta.getId());
    }

    public VotacaoDTO close(BigInteger idPauta) {
        Optional<Pauta> pautaOpt = pRep.findById(idPauta);
        Assert.isTrue(pautaOpt.isPresent(), "Pauta não encontrada.");
        Pauta pauta = pautaOpt.get();

        // Fecha para não receber mais votos.
        pauta.setDthFimVotacao(LocalDateTime.now());
        pRep.save(pauta);

        VotacaoDTO vDto = vRep.sumVotes(pauta.getId());
        log.info("Votação " + vDto.getIdPauta() + ": S=" + vDto.getQtdSim() + ", N=" + vDto.getQtdNao());
        return vDto;
    }

}

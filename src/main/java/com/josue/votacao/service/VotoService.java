package com.josue.votacao.service;

import com.google.gson.GsonBuilder;
import com.josue.votacao.dto.StatusDTO;
import com.josue.votacao.dto.VotoDTO;
import com.josue.votacao.model.Pauta;
import com.josue.votacao.model.Voto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VotoService {

    final String urlCpf = "https://user-info.herokuapp.com/users/";
    final String STATUS_APTO = "ABLE_TO_VOTE";
    private Logger log = LoggerFactory.getLogger(VotoService.class);
    @Autowired
    private PautaRepository pRep;

    @Autowired
    private VotoRepository vRep;

    public Voto insert(VotoDTO votoDto) {
        // TODO melhoria: verificar se o CPF é válido.

        // Validações locais - Pauta
        Assert.notNull(votoDto.getIdPauta(), "Pauta não informada.");
        Optional<Pauta> pautaOpt = pRep.findById(votoDto.getIdPauta());
        Assert.isTrue(pautaOpt.isPresent(), "Pauta não encontrada.");
        Assert.notNull(pautaOpt.get().getDthFimVotacao(), "Pauta não iniciada.");
        Assert.isTrue(LocalDateTime.now().isBefore(pautaOpt.get().getDthFimVotacao()), "Pauta encerrada.");

        // Validações locais - Voto
        Optional<Voto> votoOpt = vRep.findVoto(votoDto.getIdPauta(), votoDto.getCpfAssociado());
        Assert.isTrue(!votoOpt.isPresent(), "Voto já registrado.");

        // Validações externas
        try {
            String strJson = new RestTemplate().getForObject(urlCpf + votoDto.getCpfAssociado(), String.class);
            StatusDTO status = new GsonBuilder().create().fromJson(strJson, StatusDTO.class);
            Assert.isTrue(STATUS_APTO.equals(status.getStatus()), "CPF impossibilitado de votar.");
        } catch (Exception e) {
            throw new IllegalArgumentException("CPF inválido ou impossibilitado de votar.");
        }

        // Converte DTO na entidade
        ModelMapper modelMapper = new ModelMapper();
        Voto voto = modelMapper.map(votoDto, Voto.class);

        log.info("Registrando - Pauta=" + voto.getIdPauta() + " Voto=" + voto.getIndVotoSim());
        return vRep.save(voto);
    }

}

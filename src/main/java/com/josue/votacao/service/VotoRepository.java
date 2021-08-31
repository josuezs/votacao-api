package com.josue.votacao.service;

import com.josue.votacao.dto.VotacaoDTO;
import com.josue.votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, BigInteger> {

    static final String QUERY_SUM =
            "SELECT new com.josue.votacao.dto.VotacaoDTO(p.id, p.desPauta," +
                    " (SELECT count(1) FROM voto v where v.idPauta = p.id and v.indVotoSim = 1)," +
                    " (SELECT count(1) FROM voto v where v.idPauta = p.id and v.indVotoSim != 1))" +
                    " FROM pauta p" +
                    " WHERE p.id = ?1";

    @Query("SELECT e FROM voto e WHERE e.idPauta = ?1 AND e.cpfAssociado = ?2")
    Optional<Voto> findVoto(BigInteger idPauta, Long cpf);

    void deleteByIdPauta(BigInteger id);

    @Query(QUERY_SUM)
    VotacaoDTO sumVotes(BigInteger id);
}

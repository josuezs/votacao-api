package com.josue.votacao.service;

import com.josue.votacao.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PautaRepository extends JpaRepository<Pauta, BigInteger> {

    Pauta findByDesPautaIgnoreCase(String desPauta);

}

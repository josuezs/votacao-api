package com.josue.votacao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity(name = "pauta")
@Table(name = "pauta")
@SequenceGenerator(name = "seqPauta", sequenceName = "pauta_seq", allocationSize = 1)
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPauta")
    private BigInteger id;

    @Column(name = "des_pauta", nullable = false, length = 50)
    private String desPauta;

    @Column(name = "dth_fim_votacao")
    private LocalDateTime dthFimVotacao;

}

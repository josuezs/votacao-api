package com.josue.votacao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@NoArgsConstructor
@Data
@Entity(name = "voto")
@Table(name = "voto")
@SequenceGenerator(name = "seqVoto", sequenceName = "voto_seq", allocationSize = 1)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqVoto")
    private BigInteger id;

    @Column(name = "id_pauta")
    private BigInteger idPauta;

    @Column(name = "cpf_associado")
    private Long cpfAssociado;

    @Column(name = "ind_voto_sim")
    private Integer indVotoSim;

}

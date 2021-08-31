-------------------------------------------------------
CREATE EXTENSION unaccent;
-------------------------------------------------------
CREATE TABLE pauta
(
  id numeric(19) NOT NULL,
  des_pauta character varying(50) NOT NULL,
  dth_fim_votacao timestamp without time zone NOT NULL,
  CONSTRAINT pauta_pk PRIMARY KEY (id)
);
ALTER TABLE pauta OWNER TO postgres;

CREATE SEQUENCE pauta_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE pauta_seq OWNER TO postgres;

CREATE TABLE voto
(
  id numeric(19) NOT NULL,
  id_pauta numeric(19) NOT NULL,
  cpf_associado numeric(11) NOT NULL,
  ind_voto_sim numeric(1) NOT NULL,
  CONSTRAINT voto_pk PRIMARY KEY (id),
  CONSTRAINT voto_fk1 FOREIGN KEY (id_pauta)
      REFERENCES pauta (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE voto OWNER TO postgres;

CREATE SEQUENCE voto_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE voto_seq OWNER TO postgres;

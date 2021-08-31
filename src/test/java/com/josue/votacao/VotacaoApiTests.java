package com.josue.votacao;

import com.josue.votacao.dto.VotoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes na API.
 */
@SpringBootTest(classes = VotacaoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VotacaoApiTests {

    private static final String URL_PAUTA = "/api/v1/pauta";
    private static final String URL_VOTO = "/api/v1/voto";
    private Logger log = LoggerFactory.getLogger(VotacaoApiTests.class);
    @Autowired
    private TestRestTemplate rest;

    @Test
    @DisplayName("Teste API para criação e abertura da pauta.")
    public void testePauta() {
        ResponseEntity resp = rest.postForEntity(URL_PAUTA + "/Pauta", null, null);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCode().value());

        resp = rest.postForEntity(URL_PAUTA + "/abrir/1/2", null, null);
        assertEquals(HttpStatus.OK.value(), resp.getStatusCode().value());
    }

    @Test
    @DisplayName("Performance API: múltiplos votos.")
    public void testePerformance() {

        ResponseEntity resp = rest.postForEntity(URL_PAUTA + "/Pauta", null, null);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCode().value());

        resp = rest.postForEntity(URL_PAUTA + "/abrir/1/2", null, null);
        assertEquals(HttpStatus.OK.value(), resp.getStatusCode().value());

        List<Long> lstCpf = new ArrayList<>(Arrays.asList(
                78873087396l, 48793075057l, 84767349052l, 93181950017l, 26623497005l,
                79002791054l, 77506961059l, 5966858071l, 30444388028l, 36591327053l,
                38505857003l, 55754901062l, 79394357017l)) {
        };

        // TODO melhoria: implementar threads para realizar teste de votos paralelos (simultâneos) e não somente sequenciais.

        VotoDTO voto = null;
        for (int i = 0; i < lstCpf.size(); i++) {
            log.info("Registrando voto " + i);
            voto = new VotoDTO(BigInteger.ONE, lstCpf.get(i), (i % 2 == 0 ? 1 : 0));
            rest.postForEntity(URL_VOTO, voto, null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        resp = rest.exchange(URL_PAUTA+ "/totalizar/1", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK.value(), resp.getStatusCode().value());
    }

}

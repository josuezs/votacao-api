package com.josue.votacao.api;

import com.josue.votacao.dto.VotacaoDTO;
import com.josue.votacao.model.Pauta;
import com.josue.votacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController extends AbstractController {

    @Autowired
    private PautaService service;

    @PostMapping("/{desPauta}")
    public ResponseEntity insert(@PathVariable("desPauta") String desPauta) {
        Pauta pauta = service.insert(desPauta);
        URI location = getURI(pauta.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable("id") BigInteger id) {
        try {
            Pauta pauta = service.findById(id);
            return ResponseEntity.ok(pauta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/abrir/{id}/{qtdMinutos}")
    public ResponseEntity open(@PathVariable("id") BigInteger idPauta, @PathVariable("qtdMinutos") Integer qtdMinutos) {
        service.open(idPauta, qtdMinutos);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/totalizar/{id}")
    public ResponseEntity close(@PathVariable("id") BigInteger idPauta) {
        VotacaoDTO votacaoDto = service.close(idPauta);
        return ResponseEntity.ok(votacaoDto);
    }

}

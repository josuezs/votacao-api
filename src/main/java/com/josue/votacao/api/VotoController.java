package com.josue.votacao.api;

import com.josue.votacao.dto.VotoDTO;
import com.josue.votacao.model.Voto;
import com.josue.votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/voto")
public class VotoController extends AbstractController {

    @Autowired
    private VotoService service;

    @PostMapping("")
    public ResponseEntity insert(@RequestBody VotoDTO votoDto) {
        Voto voto = service.insert(votoDto);
        URI location = getURI(voto.getId());
        return ResponseEntity.created(location).build();
    }

}

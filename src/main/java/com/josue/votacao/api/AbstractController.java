package com.josue.votacao.api;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;

public abstract class AbstractController {

    /**
     * Cria uma URI da localização do recurso.
     */
    protected URI getURI(BigInteger id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

}

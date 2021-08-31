package com.josue.votacao.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String get() {
        return "<html>API de votação! <a href='/swagger-ui.html'> Serviços disponíveis.</a></html>";
    }

}

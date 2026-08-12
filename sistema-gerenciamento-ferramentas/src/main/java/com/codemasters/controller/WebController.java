package com.codemasters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/ferramentas")
    public String ferramentas() {
        return "ferramentas";
    }

    @GetMapping("/funcionarios")
    public String funcionarios() {
        return "funcionarios";
    }

    @GetMapping("/emprestimos")
    public String emprestimos() {
        return "emprestimos";
    }
}

package com.agendamento.clinica_agendamento.controller;

import com.agendamento.clinica_agendamento.model.Endereco;
import com.agendamento.clinica_agendamento.service.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Optional<Endereco>> findById(@PathVariable Long id){
        Optional<Endereco> endereco = enderecoService.findById(id);
        return ResponseEntity.ok().body(endereco);
    }

    @RequestMapping
    public ResponseEntity<List<Endereco>> findAll(){
        List<Endereco> listaEndereco = enderecoService.findAll();
        return ResponseEntity.ok().body(listaEndereco);
    }

}

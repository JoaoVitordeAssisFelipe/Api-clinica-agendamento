package com.agendamento.clinica_agendamento.controller;

import com.agendamento.clinica_agendamento.dto.ProntuarioRequestDTO;
import com.agendamento.clinica_agendamento.dto.ProntuarioResponseDTO;
import com.agendamento.clinica_agendamento.service.ProntuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("prontuarios")
public class ProntuarioController {
    private final ProntuarioService prontuarioService;

    public ProntuarioController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @GetMapping
    public ResponseEntity<List<ProntuarioResponseDTO>> findAll(){
        List<ProntuarioResponseDTO> listaProntuario = prontuarioService.findAll();
        return ResponseEntity.ok().body(listaProntuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProntuarioResponseDTO> findById(@PathVariable Long id){
        ProntuarioResponseDTO prontuarioID = prontuarioService.findById(id);
        return ResponseEntity.ok().body(prontuarioID);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable ProntuarioRequestDTO dto){
        ProntuarioResponseDTO novoProntuario = prontuarioService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}

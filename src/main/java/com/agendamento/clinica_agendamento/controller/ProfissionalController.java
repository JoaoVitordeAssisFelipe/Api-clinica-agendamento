package com.agendamento.clinica_agendamento.controller;

import com.agendamento.clinica_agendamento.dto.ProfissionalRequestDTO;
import com.agendamento.clinica_agendamento.dto.ProfissionalResponseDTO;
import com.agendamento.clinica_agendamento.service.ProfissionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalResponseDTO>> findAll(){
       List<ProfissionalResponseDTO> listaProfissional = profissionalService.findAll();
       return ResponseEntity.ok().body(listaProfissional);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> findById(Long id){
        ProfissionalResponseDTO profissional = profissionalService.findById(id);
        return ResponseEntity.ok().body(profissional);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProfissionalRequestDTO dto){
        ProfissionalResponseDTO profissionalResponseDTO = profissionalService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        profissionalService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

package com.agendamento.clinica_agendamento.controller;

import com.agendamento.clinica_agendamento.dto.PacienteRequestDTO;
import com.agendamento.clinica_agendamento.dto.PacienteResponseDTO;
import com.agendamento.clinica_agendamento.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @RequestMapping
    public ResponseEntity<List<PacienteResponseDTO>> findAll(){
        List<PacienteResponseDTO> paciente = pacienteService.findAll();
        return ResponseEntity.ok().body(paciente);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> findById(@PathVariable Long id){
        PacienteResponseDTO paciente = pacienteService.findById(id);
        return ResponseEntity.ok().body(paciente);
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> create(@RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO pacienteCriado = pacienteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pacienteCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> update(@PathVariable Long id, @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO pacienteAtualizado = pacienteService.update(id, dto);
        return ResponseEntity.ok(pacienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

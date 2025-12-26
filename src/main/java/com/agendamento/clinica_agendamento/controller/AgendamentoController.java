package com.agendamento.clinica_agendamento.controller;

import com.agendamento.clinica_agendamento.dto.AgendamentoRequestDTO;
import com.agendamento.clinica_agendamento.dto.AgendamentoResponseDTO;
import com.agendamento.clinica_agendamento.model.Agendamento;
import com.agendamento.clinica_agendamento.service.AgendamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> findById(@PathVariable Long id) {
        return agendamentoService.findById(id)
                .map(agendamento -> ResponseEntity.ok(new AgendamentoResponseDTO(agendamento)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> create(@RequestBody AgendamentoRequestDTO dto) {
        Agendamento agendamentoSalvo = agendamentoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AgendamentoResponseDTO(agendamentoSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> update(@PathVariable Long id, @RequestBody AgendamentoRequestDTO dto) {
        Agendamento agendamentoAtualizado = agendamentoService.update(id, dto);
        return ResponseEntity.ok(new AgendamentoResponseDTO(agendamentoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            agendamentoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

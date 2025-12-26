package com.agendamento.clinica_agendamento.dto;

import com.agendamento.clinica_agendamento.model.Agendamento;
import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        LocalDateTime dataHora,
        String status,
        String nomePaciente,
        String nomeProfissional
) {
    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getDataHora(),
                agendamento.getStatus().getDescricao(),
                agendamento.getPaciente().getNome(),
                agendamento.getProfissional().getNome()
        );
    }
}
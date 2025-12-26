package com.agendamento.clinica_agendamento.dto;

import com.agendamento.clinica_agendamento.model.Paciente;
import com.agendamento.clinica_agendamento.model.Prontuario;

import java.util.List;

public record ProntuarioResponseDTO(Long id,
                                    Paciente paciente,
                                    List<String> observacoes) {

    public ProntuarioResponseDTO(Prontuario prontuario){
        this(prontuario.getId(),
                prontuario.getPaciente(),
                prontuario.getObservacoes()
        );
    }
}

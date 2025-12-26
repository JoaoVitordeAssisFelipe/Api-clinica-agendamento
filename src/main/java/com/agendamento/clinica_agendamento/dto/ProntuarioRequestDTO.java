package com.agendamento.clinica_agendamento.dto;

import com.agendamento.clinica_agendamento.model.Paciente;

import java.util.List;

public record ProntuarioRequestDTO (Paciente paciente,
                                    List<String> observacoes){
}

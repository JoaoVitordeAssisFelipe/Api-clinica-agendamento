package com.agendamento.clinica_agendamento.dto;

import com.agendamento.clinica_agendamento.model.Paciente;
import com.agendamento.clinica_agendamento.model.Profissional;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(LocalDateTime dataHora,
                                    Long  paciente,
                                    Long profissional
){

}

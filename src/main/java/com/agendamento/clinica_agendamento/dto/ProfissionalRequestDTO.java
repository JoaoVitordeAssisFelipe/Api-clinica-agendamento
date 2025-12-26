package com.agendamento.clinica_agendamento.dto;

import com.agendamento.clinica_agendamento.model.Endereco;

import java.time.LocalDate;

public record ProfissionalRequestDTO(String registroMedico,
                                     String nome,
                                     String cpf,
                                     String telefone,
                                     String email,
                                     LocalDate dataNascimento,
                                     Endereco endereco) {
}

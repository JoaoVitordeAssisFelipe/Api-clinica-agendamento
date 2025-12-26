package com.agendamento.clinica_agendamento.dto;

import com.agendamento.clinica_agendamento.model.Endereco;
import com.agendamento.clinica_agendamento.model.Paciente;

import java.time.LocalDate;

public record PacienteResponseDTO (Long id,
                                  String nome,
                                  String cpf,
                                  String telefone,
                                  String email,
                                  LocalDate dataNascimento,
                                  EnderecoResponseDTO endereco){

    public PacienteResponseDTO(Paciente paciente){
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getEmail(),
                paciente.getDataNascimento(),
                paciente.getEndereco() != null ? new EnderecoResponseDTO(paciente.getEndereco()) : null
        );
    }

}

package com.agendamento.clinica_agendamento.dto;

import com.agendamento.clinica_agendamento.model.Endereco;
import com.agendamento.clinica_agendamento.model.Profissional;

import java.time.LocalDate;

public record ProfissionalResponseDTO(
                                      String registroMedico,
                                      String nome,
                                      String cpf,
                                      String telefone,
                                      String email,
                                      LocalDate dataNascimento,
                                      Endereco endereco) {

    public ProfissionalResponseDTO(Profissional profissional){
        this(
                profissional.getRegistroMedico(),
                profissional.getNome(),
                profissional.getCpf(),
                profissional.getTelefone(),
                profissional.getEmail(),
                profissional.getDataNascimento(),
                profissional.getEndereco()
        );
    }
}

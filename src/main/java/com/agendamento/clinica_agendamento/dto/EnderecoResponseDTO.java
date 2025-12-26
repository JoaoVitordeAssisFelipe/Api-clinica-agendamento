package com.agendamento.clinica_agendamento.dto;

import com.agendamento.clinica_agendamento.model.Endereco;

public record EnderecoResponseDTO(
                                  String logradouro,
                                  String cep,
                                  String cidade,
                                  String estado) {

    public EnderecoResponseDTO(Endereco endereco){
        this(
                endereco.getLogradouro(),
                endereco.getCep(),
                endereco.getCidade(),
                endereco.getEstado()
        );
    }

}

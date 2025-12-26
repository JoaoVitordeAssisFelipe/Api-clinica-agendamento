package com.agendamento.clinica_agendamento.dto;

public record EnderecoRequestDTO(String logradouro,
                                 String cep,
                                 String cidade,
                                 String estado) {
}

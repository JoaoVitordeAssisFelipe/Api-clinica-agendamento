package com.agendamento.clinica_agendamento.model;

import lombok.Getter;

@Getter
public enum TipoAgendamento {

    AGUARDANDO("Aguardando Confirmação"),
    CONFIRMADO("Confirmado"),
    CANCELADO("Cancelado"),
    CONCLUIDO("Concluído");

    private final String descricao;

    TipoAgendamento(String descricao) {
        this.descricao = descricao;
    }
}
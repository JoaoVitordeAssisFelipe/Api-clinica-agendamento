package com.agendamento.clinica_agendamento.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_profissional")
public class Profissional extends Pessoa {

    @Column(unique = true)
    private String registroMedico;

    @OneToMany(mappedBy = "profissional")
    private List<Agendamento> listaAgendamento = new ArrayList<>();
}
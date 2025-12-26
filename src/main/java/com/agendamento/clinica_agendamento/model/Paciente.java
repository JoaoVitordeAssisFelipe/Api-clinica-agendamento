package com.agendamento.clinica_agendamento.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_paciente")
public class Paciente extends Pessoa {

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Agendamento> historicoAgendamentos = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;
}
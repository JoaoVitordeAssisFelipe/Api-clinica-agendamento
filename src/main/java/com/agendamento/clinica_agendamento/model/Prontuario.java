package com.agendamento.clinica_agendamento.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_prontuario")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "prontuario")
    private Paciente paciente;

    @ElementCollection
    @CollectionTable(name = "tb_prontuario_observacoes", joinColumns = @JoinColumn(name = "prontuario_id"))
    @Column(name = "observacao")
    private List<String> observacoes = new ArrayList<>();

}
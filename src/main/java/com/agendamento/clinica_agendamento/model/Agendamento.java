package com.agendamento.clinica_agendamento.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tb_agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @Enumerated(EnumType.STRING)
    private TipoAgendamento status;

    public void reagendar(LocalDateTime novaData){
        this.dataHora = novaData;
        this.status = TipoAgendamento.AGUARDANDO;
    }

    public void cancelar(){
        this.status = TipoAgendamento.CANCELADO;
    }

}

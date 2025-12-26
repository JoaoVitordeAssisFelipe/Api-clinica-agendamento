package com.agendamento.clinica_agendamento.repository;

import com.agendamento.clinica_agendamento.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {
}

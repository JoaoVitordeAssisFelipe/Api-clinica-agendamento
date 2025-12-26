package com.agendamento.clinica_agendamento.repository;

import com.agendamento.clinica_agendamento.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
}

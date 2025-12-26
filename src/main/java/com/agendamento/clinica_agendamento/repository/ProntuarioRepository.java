package com.agendamento.clinica_agendamento.repository;

import com.agendamento.clinica_agendamento.model.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
}

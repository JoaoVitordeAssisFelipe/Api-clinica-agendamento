package com.agendamento.clinica_agendamento.repository;

import com.agendamento.clinica_agendamento.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {
}

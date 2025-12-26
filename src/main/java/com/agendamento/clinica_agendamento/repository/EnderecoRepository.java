package com.agendamento.clinica_agendamento.repository;

import com.agendamento.clinica_agendamento.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}

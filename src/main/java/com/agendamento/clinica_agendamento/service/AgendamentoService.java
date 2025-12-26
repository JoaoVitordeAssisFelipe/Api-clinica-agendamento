package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.AgendamentoRequestDTO;
import com.agendamento.clinica_agendamento.model.Agendamento;
import com.agendamento.clinica_agendamento.model.Paciente;
import com.agendamento.clinica_agendamento.model.Profissional;
import com.agendamento.clinica_agendamento.model.TipoAgendamento;
import com.agendamento.clinica_agendamento.repository.AgendamentoRepository;
import com.agendamento.clinica_agendamento.repository.PacienteRepository;
import com.agendamento.clinica_agendamento.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, PacienteRepository pacienteRepository, ProfissionalRepository profissionalRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

   public Optional<Agendamento> findById(Long id){
        return agendamentoRepository.findById(id);
   }

    public List<Agendamento> findAll(){
        return agendamentoRepository.findAll();
    }

    public Agendamento create(AgendamentoRequestDTO dto) {
        Agendamento agendamento = new Agendamento();

        agendamento.setDataHora(dto.dataHora());
        agendamento.setStatus(TipoAgendamento.AGUARDANDO);

        Paciente paciente = pacienteRepository.findById(dto.paciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));
        agendamento.setPaciente(paciente);

        Profissional profissional = profissionalRepository.findById(dto.profissional())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!"));
        agendamento.setProfissional(profissional);

        return agendamentoRepository.save(agendamento);
    }

    public void deleteById(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado para exclusão!");
        }
        agendamentoRepository.deleteById(id);
    }

    public Agendamento update(Long id, AgendamentoRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        agendamento.setDataHora(dto.dataHora());

        if (dto.paciente() != null) {
            Paciente paciente = pacienteRepository.findById(dto.paciente())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
            agendamento.setPaciente(paciente);
        }
        if (dto.paciente() != null) {
            Profissional profissional = profissionalRepository.findById(dto.paciente())
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
            agendamento.setProfissional(profissional);
        }

        return agendamentoRepository.save(agendamento);

    }
}

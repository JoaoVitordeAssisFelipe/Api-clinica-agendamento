package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.ProntuarioRequestDTO;
import com.agendamento.clinica_agendamento.dto.ProntuarioResponseDTO;
import com.agendamento.clinica_agendamento.model.Prontuario;
import com.agendamento.clinica_agendamento.repository.ProntuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProntuarioService {
    private final ProntuarioRepository prontuarioRepository;

    public ProntuarioService(ProntuarioRepository prontuarioRepository) {
        this.prontuarioRepository = prontuarioRepository;
    }

    public List<ProntuarioResponseDTO> findAll() {
        return prontuarioRepository.findAll().
                stream()
                .map(ProntuarioResponseDTO::new)
                .toList();
    }

    public ProntuarioResponseDTO findById(Long id) {
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));
        return new ProntuarioResponseDTO(prontuario);

    }

    public ProntuarioResponseDTO create(ProntuarioRequestDTO dto) {
        Prontuario novoProntuario = new Prontuario();

        novoProntuario.setPaciente(dto.paciente());
        novoProntuario.setObservacoes(dto.observacoes());

        Prontuario prontuarioSalvo = prontuarioRepository.save(novoProntuario);

        return new ProntuarioResponseDTO(prontuarioSalvo);
    }
}

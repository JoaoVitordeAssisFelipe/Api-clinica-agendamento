package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.ProfissionalRequestDTO;
import com.agendamento.clinica_agendamento.dto.ProfissionalResponseDTO;
import com.agendamento.clinica_agendamento.model.Profissional;
import com.agendamento.clinica_agendamento.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalService {
    private final ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public List<ProfissionalResponseDTO> findAll() {
        List<Profissional> listaProfissionais = profissionalRepository.findAll();
        return listaProfissionais.stream()
                .map(ProfissionalResponseDTO::new)
                .toList();
    }

    public ProfissionalResponseDTO findById(Long id){
        Profissional profissionalId = profissionalRepository.findById(id).
                orElseThrow(() ->new RuntimeException("Profissional não encontrado."));
        return new ProfissionalResponseDTO(profissionalId);
    }

    public ProfissionalResponseDTO create(ProfissionalRequestDTO dto) {
        Profissional profissional = new Profissional();
        profissional.setRegistroMedico(dto.registroMedico());
        profissional.setNome(dto.nome());
        profissional.setCpf(dto.cpf());
        profissional.setTelefone(dto.telefone());
        profissional.setEmail(dto.email());
        profissional.setDataNascimento(dto.dataNascimento());
        profissional.setEndereco(dto.endereco());

        Profissional profissionalSalvo = profissionalRepository.save(profissional);

        return new ProfissionalResponseDTO(profissionalSalvo);
    }

    public void delete(Long id){
        if(!profissionalRepository.existsById(id)){
            throw new RuntimeException("Profissional não encontrado");
        }
        profissionalRepository.deleteById(id);
    }
}

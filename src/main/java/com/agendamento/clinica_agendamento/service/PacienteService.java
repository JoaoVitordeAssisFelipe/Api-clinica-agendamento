package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.EnderecoResponseDTO;
import com.agendamento.clinica_agendamento.dto.PacienteRequestDTO;
import com.agendamento.clinica_agendamento.dto.PacienteResponseDTO;
import com.agendamento.clinica_agendamento.model.Endereco;
import com.agendamento.clinica_agendamento.model.Paciente;
import com.agendamento.clinica_agendamento.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<PacienteResponseDTO> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteResponseDTO::new)
                .toList();
    }

    public PacienteResponseDTO findById(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Paciente não encontrato"));
        return new PacienteResponseDTO(paciente);
    }

    public PacienteResponseDTO create(PacienteRequestDTO dto){
        Paciente paciente = new Paciente();

        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setTelefone(dto.telefone());
        paciente.setEmail(dto.email());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setEndereco(dto.endereco());

        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        return new PacienteResponseDTO(pacienteSalvo);
    }

    public PacienteResponseDTO update(Long id, PacienteRequestDTO dto) {
        Paciente pacienteBanco = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));

        pacienteBanco.setNome(dto.nome());
        pacienteBanco.setCpf(dto.cpf());
        pacienteBanco.setEmail(dto.email());
        pacienteBanco.setTelefone(dto.telefone());
        pacienteBanco.setDataNascimento(dto.dataNascimento());

        if (dto.endereco() != null) {
            Endereco enderecoBanco = pacienteBanco.getEndereco();

            if (enderecoBanco == null) {
                enderecoBanco = new Endereco();
                pacienteBanco.setEndereco(enderecoBanco);
            }

            enderecoBanco.setLogradouro(dto.endereco().getLogradouro());
            enderecoBanco.setCep(dto.endereco().getCep());
            enderecoBanco.setCidade(dto.endereco().getCidade());
            enderecoBanco.setEstado(dto.endereco().getEstado());
        }

        Paciente pacienteSalvo = pacienteRepository.save(pacienteBanco);

        EnderecoResponseDTO enderecoResponse = null;

        if (pacienteSalvo.getEndereco() != null) {
            enderecoResponse = new EnderecoResponseDTO(
                    pacienteSalvo.getEndereco().getLogradouro(),
                    pacienteSalvo.getEndereco().getCep(),
                    pacienteSalvo.getEndereco().getCidade(),
                    pacienteSalvo.getEndereco().getEstado()
            );
        }

        return new PacienteResponseDTO(
                pacienteSalvo.getId(),
                pacienteSalvo.getNome(),
                pacienteSalvo.getCpf(),
                pacienteSalvo.getEmail(),
                pacienteSalvo.getTelefone(),
                pacienteSalvo.getDataNascimento(),
                enderecoResponse
        );
    }

    public void delete(Long dto) {
        if (!pacienteRepository.existsById(dto)){
            throw new RuntimeException("Paciente não encontrado para exclusão!");
        }
       pacienteRepository.deleteById(dto);
    }
}

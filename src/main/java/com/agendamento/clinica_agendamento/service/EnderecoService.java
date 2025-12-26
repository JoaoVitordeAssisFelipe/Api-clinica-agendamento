package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.EnderecoResponseDTO;
import com.agendamento.clinica_agendamento.model.Endereco;
import com.agendamento.clinica_agendamento.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Optional<Endereco> findById(Long id) {
        return enderecoRepository.findById(id);
    }

    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public EnderecoResponseDTO create(EnderecoResponseDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setCep(dto.cep());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setLogradouro(dto.logradouro());

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        return new EnderecoResponseDTO(
                enderecoSalvo.getLogradouro(),
                enderecoSalvo.getCep(),
                enderecoSalvo.getCidade(),
                enderecoSalvo.getEstado()
        );
    }
}

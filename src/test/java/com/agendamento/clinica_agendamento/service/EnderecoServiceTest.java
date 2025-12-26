package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.EnderecoResponseDTO;
import com.agendamento.clinica_agendamento.model.Endereco;
import com.agendamento.clinica_agendamento.repository.EnderecoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    void deveCriarEnderecoComSucesso() {

        EnderecoResponseDTO dtoEntrada = new EnderecoResponseDTO(
                "Av. Afonso Pena", "30000-000", "Belo Horizonte", "MG"
        );

        Endereco enderecoSalvo = new Endereco();
        enderecoSalvo.setId(1L);
        enderecoSalvo.setCep("30000-000");
        enderecoSalvo.setCidade("Belo Horizonte");
        enderecoSalvo.setEstado("MG");
        enderecoSalvo.setLogradouro("Av. Afonso Pena");

        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(enderecoSalvo);

        EnderecoResponseDTO resultado = enderecoService.create(dtoEntrada);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Belo Horizonte", resultado.cidade());
        Assertions.assertEquals("30000-000", resultado.cep());

        Mockito.verify(enderecoRepository, Mockito.times(1)).save(Mockito.any(Endereco.class));
    }

    @Test
    void deveBuscarEnderecoPorId() {

        Long idBusca = 1L;
        Endereco enderecoBanco = new Endereco();
        enderecoBanco.setId(idBusca);
        enderecoBanco.setCidade("Ouro Preto");

        Mockito.when(enderecoRepository.findById(idBusca)).thenReturn(Optional.of(enderecoBanco));

        Optional<Endereco> resultado = enderecoService.findById(idBusca);

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals("Ouro Preto", resultado.get().getCidade());
        Assertions.assertEquals(1L, resultado.get().getId());
    }
}
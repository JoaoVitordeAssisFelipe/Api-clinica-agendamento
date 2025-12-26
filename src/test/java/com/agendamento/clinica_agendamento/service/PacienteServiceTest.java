package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.PacienteRequestDTO;
import com.agendamento.clinica_agendamento.dto.PacienteResponseDTO;
import com.agendamento.clinica_agendamento.model.Endereco;
import com.agendamento.clinica_agendamento.model.Paciente;
import com.agendamento.clinica_agendamento.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;


    @Test
    void deveCriarPacienteComSucesso() {

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Teste");

        PacienteRequestDTO requestDTO = new PacienteRequestDTO(
                "João Silva", "123.456.789-00", "joao@email.com", "99999-9999", LocalDate.now(), endereco
        );

        Paciente pacienteSalvo = new Paciente();
        pacienteSalvo.setId(1L);
        pacienteSalvo.setNome("João Silva");
        pacienteSalvo.setCpf("123.456.789-00");
        pacienteSalvo.setEndereco(endereco);

        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteSalvo);

        PacienteResponseDTO resposta = pacienteService.create(requestDTO);

        assertNotNull(resposta);
        assertEquals(1L, resposta.id()); // Verifica se retornou o ID
        assertEquals("João Silva", resposta.nome());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void deveBuscarPacientePorIdComSucesso() {
        // ARRANGE
        Long id = 1L;
        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNome("Maria");

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(paciente));

        PacienteResponseDTO resultado = pacienteService.findById(id);

        assertEquals("Maria", resultado.nome());
    }

    @Test
    void deveLancarErroAoBuscarIdInexistente() {
        Long id = 99L;
        when(pacienteRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.findById(id);
        });

        assertEquals("Paciente não encontrato", exception.getMessage());
    }

    @Test
    void deveAtualizarPacienteComSucesso() {

        Long id = 1L;

        Paciente pacienteAntigo = new Paciente();
        pacienteAntigo.setId(id);
        pacienteAntigo.setNome("Nome Antigo");
        pacienteAntigo.setEndereco(new Endereco());

        Endereco enderecoNovo = new Endereco();
        enderecoNovo.setCidade("Nova Cidade");

        PacienteRequestDTO requestDTO = new PacienteRequestDTO(
                "Nome Novo", "111.111.111-11", "novo@email.com", "88888-8888", LocalDate.now(), enderecoNovo
        );

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(pacienteAntigo));

        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PacienteResponseDTO resultado = pacienteService.update(id, requestDTO);

        assertEquals("Nome Novo", resultado.nome()); // Nome mudou?
        assertNotNull(resultado.endereco());
        assertEquals("Nova Cidade", resultado.endereco().cidade()); // Endereço mudou?

        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }


    @Test
    void deveDeletarPacienteComSucesso() {

        Long id = 1L;
        when(pacienteRepository.existsById(id)).thenReturn(true);

        pacienteService.delete(id);

        verify(pacienteRepository, times(1)).deleteById(id);
    }

    @Test
    void deveFalharAoDeletarPacienteInexistente() {

        Long id = 99L;
        when(pacienteRepository.existsById(id)).thenReturn(false);

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            pacienteService.delete(id);
        });

        assertEquals("Paciente não encontrado para exclusão!", erro.getMessage());
        verify(pacienteRepository, never()).deleteById(id);
    }

    @Test
    void deveListarTodosPacientes() {

        Paciente p1 = new Paciente(); p1.setNome("A");
        Paciente p2 = new Paciente(); p2.setNome("B");

        when(pacienteRepository.findAll()).thenReturn(List.of(p1, p2));

        List<PacienteResponseDTO> lista = pacienteService.findAll();

        assertEquals(2, lista.size());
        assertEquals("A", lista.get(0).nome());
    }
}
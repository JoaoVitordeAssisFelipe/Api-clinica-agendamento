package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.ProntuarioRequestDTO;
import com.agendamento.clinica_agendamento.dto.ProntuarioResponseDTO;
import com.agendamento.clinica_agendamento.model.Paciente;
import com.agendamento.clinica_agendamento.model.Prontuario;
import com.agendamento.clinica_agendamento.repository.ProntuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProntuarioServiceTest {

    @Mock
    private ProntuarioRepository prontuarioRepository;

    @InjectMocks
    private ProntuarioService prontuarioService;

    @Test
    void deveCriarProntuarioComSucesso() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente Teste");

        String observacao = "Paciente apresenta sintomas de gripe.";
        List<String> listaObservacoes = List.of(observacao);
        ProntuarioRequestDTO dto = new ProntuarioRequestDTO(paciente, listaObservacoes);

        Prontuario prontuarioSalvo = new Prontuario();
        prontuarioSalvo.setId(10L);
        prontuarioSalvo.setPaciente(paciente);
        prontuarioSalvo.setObservacoes(listaObservacoes);

        when(prontuarioRepository.save(any(Prontuario.class))).thenReturn(prontuarioSalvo);

        ProntuarioResponseDTO resultado = prontuarioService.create(dto);

        assertNotNull(resultado);
        assertEquals(listaObservacoes, resultado.observacoes());
        assertEquals("Paciente Teste", resultado.paciente().getNome());

        verify(prontuarioRepository, times(1)).save(any(Prontuario.class));
    }

    @Test
    void deveBuscarProntuarioPorId() {
        Long id = 1L;
        Prontuario prontuario = new Prontuario();
        prontuario.setId(id);
        List<String> obs = List.of("Obs teste");
        prontuario.setObservacoes(obs);

        Paciente p = new Paciente();
        p.setNome("Jose");
        prontuario.setPaciente(p);

        when(prontuarioRepository.findById(id)).thenReturn(Optional.of(prontuario));

        ProntuarioResponseDTO resultado = prontuarioService.findById(id);

        // CORREÇÃO: Verificamos se a lista retornada contém o que esperamos
        assertEquals(obs, resultado.observacoes());
        assertEquals("Jose", resultado.paciente().getNome());
    }

    @Test
    void deveListarTodosProntuarios() {
        Prontuario p1 = new Prontuario();
        p1.setObservacoes(List.of("Obs 1"));
        p1.setPaciente(new Paciente());

        Prontuario p2 = new Prontuario();
        p2.setObservacoes(List.of("Obs 2"));
        p2.setPaciente(new Paciente());

        when(prontuarioRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProntuarioResponseDTO> lista = prontuarioService.findAll();

        assertEquals(2, lista.size());
        assertEquals(List.of("Obs 1"), lista.get(0).observacoes());
    }

    @Test
    void deveLancarErroAoBuscarIdInexistente() {
        Long id = 99L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            prontuarioService.findById(id);
        });

        assertEquals("Id não encontrado", erro.getMessage());
    }
}
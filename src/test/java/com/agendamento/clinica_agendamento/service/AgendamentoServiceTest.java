package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.AgendamentoRequestDTO;
import com.agendamento.clinica_agendamento.model.*;
import com.agendamento.clinica_agendamento.repository.AgendamentoRepository;
import com.agendamento.clinica_agendamento.repository.PacienteRepository;
import com.agendamento.clinica_agendamento.repository.ProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private AgendamentoService service;

    @Test
    void deveBuscarAgendamentoPorId() {
        Long id = 1L;
        Agendamento agendamento = new Agendamento();
        agendamento.setId(id);

        Mockito.when(agendamentoRepository.findById(id))
                .thenReturn(Optional.of(agendamento));

        Optional<Agendamento> result = service.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        Mockito.verify(agendamentoRepository).findById(id);
    }

    @Test
    void deveBuscarTodosAgendamentos() {
        Mockito.when(agendamentoRepository.findAll())
                .thenReturn(List.of(new Agendamento(), new Agendamento()));

        List<Agendamento> result = service.findAll();

        assertEquals(2, result.size());
        Mockito.verify(agendamentoRepository).findAll();
    }

    @Test
    void deveCriarAgendamentoComSucesso() {

        LocalDateTime dataHora = LocalDateTime.of(2025, 3, 10, 14, 30);
        Long pacienteId = 1L;
        Long profissionalId = 2L;

        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);

        Profissional profissional = new Profissional();
        profissional.setId(profissionalId);

        AgendamentoRequestDTO dto = new AgendamentoRequestDTO(dataHora, pacienteId, profissionalId);

        Mockito.when(pacienteRepository.findById(pacienteId))
                .thenReturn(Optional.of(paciente));

        Mockito.when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        Mockito.when(agendamentoRepository.save(any(Agendamento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Agendamento result = service.create(dto);

        assertNotNull(result);
        assertEquals(dataHora, result.getDataHora());
        assertEquals(TipoAgendamento.AGUARDANDO, result.getStatus());
        assertEquals(paciente, result.getPaciente());
        assertEquals(profissional, result.getProfissional());
    }

    @Test
    void deveLancarErroQuandoPacienteNaoEncontradoNoCreate() {

        AgendamentoRequestDTO dto = new AgendamentoRequestDTO(LocalDateTime.now(), 1L, 2L);

        Mockito.when(pacienteRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.create(dto)
        );

        assertEquals("Paciente não encontrado!", exception.getMessage());
    }

    @Test
    void deveDeletarAgendamentoComSucesso() {
        Long id = 1L;

        Mockito.when(agendamentoRepository.existsById(id))
                .thenReturn(true);

        service.deleteById(id);

        Mockito.verify(agendamentoRepository).deleteById(id);
    }

    @Test
    void deveLancarErroAoDeletarAgendamentoInexistente() {
        Long id = 1L;

        Mockito.when(agendamentoRepository.existsById(id))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.deleteById(id)
        );

        assertEquals("Agendamento não encontrado para exclusão!", exception.getMessage());
    }

    @Test
    void deveAtualizarAgendamentoComSucesso() {
        Long idAgendamento = 10L;

        Long idPaciente = 50L;
        Long idProfissional = 99L;

        LocalDateTime novaData = LocalDateTime.of(2025, 4, 1, 10, 0);

        Agendamento agendamentoAntigo = new Agendamento();
        agendamentoAntigo.setId(idAgendamento);
        agendamentoAntigo.setStatus(TipoAgendamento.AGUARDANDO);

        Paciente pacienteRetorno = new Paciente();
        pacienteRetorno.setId(idPaciente);

        Profissional profissionalRetorno = new Profissional();
        profissionalRetorno.setId(idProfissional);

        AgendamentoRequestDTO dto = new AgendamentoRequestDTO(novaData, idPaciente, idProfissional);

        Mockito.when(agendamentoRepository.findById(idAgendamento))
                .thenReturn(Optional.of(agendamentoAntigo));

        Mockito.when(pacienteRepository.findById(idPaciente))
                .thenReturn(Optional.of(pacienteRetorno));

        Mockito.when(profissionalRepository.findById(idProfissional))
                .thenReturn(Optional.of(profissionalRetorno));

        Mockito.when(agendamentoRepository.save(Mockito.any(Agendamento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Agendamento result = service.update(idAgendamento, dto);

        assertNotNull(result);
        assertEquals(novaData, result.getDataHora());
        assertEquals(idPaciente, result.getPaciente().getId());
        assertEquals(idProfissional, result.getProfissional().getId());
    }

    @Test
    void deveLancarErroQuandoAgendamentoNaoEncontradoNoUpdate() {
        Long idInexistente = 99L;
        AgendamentoRequestDTO dto = new AgendamentoRequestDTO(LocalDateTime.now(), 1L, 1L);

        Mockito.when(agendamentoRepository.findById(idInexistente))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.update(idInexistente, dto)
        );

        assertEquals("Agendamento não encontrado", exception.getMessage());
    }
}
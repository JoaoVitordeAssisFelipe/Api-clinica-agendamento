package com.agendamento.clinica_agendamento.service;

import com.agendamento.clinica_agendamento.dto.ProfissionalRequestDTO;
import com.agendamento.clinica_agendamento.dto.ProfissionalResponseDTO;
import com.agendamento.clinica_agendamento.model.Endereco;
import com.agendamento.clinica_agendamento.model.Profissional;
import com.agendamento.clinica_agendamento.repository.ProfissionalRepository;
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
class ProfissionalServiceTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private ProfissionalService profissionalService;

    @Test
    void deveCriarProfissionalComSucesso() {

        Endereco endereco = new Endereco();
        endereco.setCidade("São Paulo");

        ProfissionalRequestDTO dto = new ProfissionalRequestDTO(
                "CRM/SP 12345", "Dra. Ana", "123.456.789-00",
                "1199999-9999", "ana@email.com", LocalDate.of(1980, 5, 20), endereco
        );

        Profissional profissionalSalvo = new Profissional();
        profissionalSalvo.setId(10L);
        profissionalSalvo.setNome("Dra. Ana");
        profissionalSalvo.setRegistroMedico("CRM/SP 12345");

        when(profissionalRepository.save(any(Profissional.class))).thenReturn(profissionalSalvo);

        ProfissionalResponseDTO resultado = profissionalService.create(dto);

        assertNotNull(resultado);
        assertEquals("Dra. Ana", resultado.nome());
        assertEquals("CRM/SP 12345", resultado.registroMedico());

        verify(profissionalRepository, times(1)).save(any(Profissional.class));
    }

    @Test
    void deveBuscarProfissionalPorId() {
        Long id = 1L;
        Profissional profissional = new Profissional();
        profissional.setId(id);
        profissional.setNome("Dr. House");

        when(profissionalRepository.findById(id)).thenReturn(Optional.of(profissional));

        ProfissionalResponseDTO resultado = profissionalService.findById(id);

        assertEquals("Dr. House", resultado.nome());
        verify(profissionalRepository, times(1)).findById(id);
    }

    @Test
    void deveFalharAoBuscarIdInexistente() {

        Long id = 99L;
        when(profissionalRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            profissionalService.findById(id);
        });

        assertEquals("Profissional não encontrado.", erro.getMessage());
    }

    @Test
    void deveListarTodosProfissionais() {

        Profissional p1 = new Profissional();
        p1.setNome("Profissional A");
        Profissional p2 = new Profissional();
        p2.setNome("Profissional B");

        when(profissionalRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProfissionalResponseDTO> lista = profissionalService.findAll();

        assertFalse(lista.isEmpty());
        assertEquals(2, lista.size());
        assertEquals("Profissional A", lista.get(0).nome());
    }

    @Test
    void deveDeletarProfissionalComSucesso() {

        Long id = 1L;
        when(profissionalRepository.existsById(id)).thenReturn(true);

        profissionalService.delete(id);

        verify(profissionalRepository, times(1)).deleteById(id);
    }

    @Test
    void deveFalharAoDeletarInexistente() {

        Long id = 50L;
        when(profissionalRepository.existsById(id)).thenReturn(false);

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            profissionalService.delete(id);
        });

        assertEquals("Profissional não encontrado", erro.getMessage());
        verify(profissionalRepository, never()).deleteById(id);
    }
}
package com.agendamento.clinica_agendamento.controller;

// REMOVA O LOGBACK E ADICIONE ESTE:
import org.springframework.ui.Model;

import com.agendamento.clinica_agendamento.dto.ProntuarioResponseDTO;
import com.agendamento.clinica_agendamento.service.ProntuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/prontuarios")
public class ProntuarioViewController {

    @Autowired
    private ProntuarioService prontuarioService;

    @GetMapping("/{id}")
    public String exibirProntuario(@PathVariable Long id, Model model) {
        ProntuarioResponseDTO prontuario = prontuarioService.findById(id);
        model.addAttribute("prontuario", prontuario);
        return "detalhes-prontuario";
    }

    @GetMapping("/pacientes")
    public String listarPacientes(Model model) {
        // Aqui você usaria o seu pacienteService.findAll()
        // model.addAttribute("pacientes", pacienteService.findAll());
        return "lista-pacientes";
    }
}
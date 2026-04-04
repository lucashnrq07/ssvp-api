package com.lucas.ssvp_api.controllers;

import com.lucas.ssvp_api.dto.AssistidoDTO;
import com.lucas.ssvp_api.dto.CriarAssistidoDTO;
import com.lucas.ssvp_api.services.AssistidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assistidos")
public class AssistidoController {

    private final AssistidoService service;

    // CADASTRAR ASSISTIDO
    @PostMapping
    public ResponseEntity<AssistidoDTO> cadastrarAssistido(@RequestBody CriarAssistidoDTO dto) {
        return ResponseEntity.ok(service.cadastrarAssistido(dto));
    }

    // BUSCAR ASSISTIDO PELO ID
    @GetMapping("/{id}")
    public ResponseEntity<AssistidoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}

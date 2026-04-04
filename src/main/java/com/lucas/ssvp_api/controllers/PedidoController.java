package com.lucas.ssvp_api.controllers;

import com.lucas.ssvp_api.dto.PedidoDTO;
import com.lucas.ssvp_api.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    // CRIAR PEDIDO
    @PostMapping("/{assistidoId}")
    public ResponseEntity<PedidoDTO> criarPedido(@PathVariable Long assistidoId) {
        return ResponseEntity.ok(service.criarPedido(assistidoId));
    }
}

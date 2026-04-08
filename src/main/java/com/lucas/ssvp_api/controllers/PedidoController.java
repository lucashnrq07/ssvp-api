package com.lucas.ssvp_api.controllers;

import com.lucas.ssvp_api.dto.AddItemDTO;
import com.lucas.ssvp_api.dto.PedidoDTO;
import com.lucas.ssvp_api.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // ADICIONAR ITEM
    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<Void> addItem(
            @PathVariable Long pedidoId,
            @RequestBody AddItemDTO dto
    ) {
        service.addItem(pedidoId, dto.produtoId(), dto.quantidade());
        return ResponseEntity.ok().build();
    }

    // REMOVER ITEM
    @DeleteMapping("/{pedidoId}/itens/{produtoId}")
    public ResponseEntity<Void> removerItem(
            @PathVariable Long pedidoId,
            @PathVariable Long produtoId
    ) {
        service.removerItem(pedidoId, produtoId);
        return ResponseEntity.noContent().build();
    }
}

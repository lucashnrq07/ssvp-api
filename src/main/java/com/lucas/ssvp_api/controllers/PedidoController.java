package com.lucas.ssvp_api.controllers;

import com.lucas.ssvp_api.dto.AddItemDTO;
import com.lucas.ssvp_api.dto.PedidoDTO;
import com.lucas.ssvp_api.services.PedidoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    // CRIAR PEDIDO
    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(HttpServletRequest request) {

        Long assistidoId = (Long) request.getAttribute("assistidoId");

        return ResponseEntity.ok(service.criarPedido(assistidoId));
    }

    // ADICIONAR ITEM
    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<Void> addItem(
            @PathVariable Long pedidoId,
            @RequestBody AddItemDTO dto,
            HttpServletRequest request
    ) {
        Long assistidoId = (Long) request.getAttribute("assistidoId");

        service.addItem(pedidoId, dto.produtoId(), dto.quantidade(), assistidoId);

        return ResponseEntity.ok().build();
    }

    // REMOVER ITEM
    @DeleteMapping("/{pedidoId}/itens/{produtoId}")
    public ResponseEntity<Void> removerItem(
            @PathVariable Long pedidoId,
            @PathVariable Long produtoId,
            HttpServletRequest request
    ) {
        Long assistidoId = (Long) request.getAttribute("assistidoId");

        service.removerItem(pedidoId, produtoId, assistidoId);

        return ResponseEntity.noContent().build();
    }

    // FINALIZAR PEDIDO
    @PatchMapping("/{pedidoId}/finalizar")
    public ResponseEntity<PedidoDTO> finalizarPedido(
            @PathVariable Long pedidoId,
            HttpServletRequest request
    ) {
        Long assistidoId = (Long) request.getAttribute("assistidoId");

        PedidoDTO dto = service.finalizarPedido(pedidoId, assistidoId);

        return ResponseEntity.ok(dto);
    }
}

package com.lucas.ssvp_api.controllers;

import com.lucas.ssvp_api.dto.ProdutoDTO;
import com.lucas.ssvp_api.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    // LISTAR PRODUTOS
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        return ResponseEntity.ok(service.listarProdutos());
    }
}

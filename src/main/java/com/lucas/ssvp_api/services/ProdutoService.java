package com.lucas.ssvp_api.services;

import com.lucas.ssvp_api.dto.ProdutoDTO;
import com.lucas.ssvp_api.model.entities.Produto;
import com.lucas.ssvp_api.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    // MÉTODO DE CONVERSÃO
    public ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getNome(),
                produto.getPreco()
        );
    }

    // LISTAR PRODUTOS
    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtos = repository.findAll();
        List<ProdutoDTO> dtos = new ArrayList<>();

        for (Produto produto : produtos) {
            dtos.add(toDTO(produto));
        }

        return dtos;
    }
}

package com.lucas.ssvp_api.services;

import com.lucas.ssvp_api.dto.PedidoDTO;
import com.lucas.ssvp_api.model.entities.Assistido;
import com.lucas.ssvp_api.model.entities.Item;
import com.lucas.ssvp_api.model.entities.Pedido;
import com.lucas.ssvp_api.model.entities.Produto;
import com.lucas.ssvp_api.model.enums.Status;
import com.lucas.ssvp_api.repositories.AssistidoRepository;
import com.lucas.ssvp_api.repositories.ItemRepository;
import com.lucas.ssvp_api.repositories.PedidoRepository;
import com.lucas.ssvp_api.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final AssistidoRepository assistidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;

    // CRIAR PEDIDO
    public PedidoDTO criarPedido(Long assistidoId) {
        Assistido assistido = buscarAssistido(assistidoId);

        int mes = LocalDate.now().getMonthValue();
        int ano = LocalDate.now().getYear();

        validarPedidoNoMes(assistidoId, mes);

        Pedido pedido = criarPedidoEntity(assistido, mes, ano);

        repository.save(pedido);

        return toDTO(pedido);
    }

    // ADICIONAR ITEM
    public void addItem(Long pedidoId, Long produtoId, Integer quantidade) {
        Pedido pedido = buscarPedido(pedidoId);
        Produto produto = buscarProduto(produtoId);

        Optional<Item> itemExistente = buscarItemExistente(pedidoId, produtoId);

        if (itemExistente.isPresent()) {
            atualizarQuantidade(itemExistente.get(), quantidade);
        } else {
            criarNovoItem(pedido, produto, quantidade);
        }
    }

    // REMOVER ITEM
    public void removerItem(Long pedidoId, Long produtoId) {
        Pedido pedido = buscarPedido(pedidoId);
        Item item = itemRepository.buscarItem(pedidoId, produtoId)
                        .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        itemRepository.delete(item);
    }


    // ===== MÉTODOS AUXILIARES =====

    // ===== CRIAR PEDIDO =====
    // buscar assistido
    private Assistido buscarAssistido(Long id) {
        return assistidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assistido não encontrado"));
    }

    // validar pedido no mês
    private void validarPedidoNoMes(Long assistidoId, int mes) {
        if (repository.existsPedidoNoMes(assistidoId, mes)) {
            throw new RuntimeException("Já existe pedido para esse mês");
        }
    }

    // cria entidade do pedido
    private Pedido criarPedidoEntity(Assistido assistido, int mes, int ano) {
        return new Pedido(
                null,
                assistido,
                Status.AGUARDANDO_SEPARACAO,
                mes,
                ano,
                LocalDate.now()
        );
    }

    // converte entidade para DTO
    private PedidoDTO toDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getAssistido().getId(),
                pedido.getStatus(),
                pedido.getMesReferencia(),
                pedido.getAnoReferencia(),
                pedido.getDataCriacao()
        );
    }

    // ===== ADICIONAR ITEM =====
    // buscar pedido
    private Pedido buscarPedido(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    // buscar produto
    private Produto buscarProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    // buscar item existente
    private Optional<Item> buscarItemExistente(Long pedidoId, Long produtoId) {
        return itemRepository.buscarItem(pedidoId, produtoId);
    }

    // atualizar quantidade
    private void atualizarQuantidade(Item item, Integer quantidade) {
        item.setQuantidade(item.getQuantidade() + quantidade);
        itemRepository.save(item);
    }

    // criar novo item
    private void criarNovoItem(Pedido pedido, Produto produto, Integer quantidade) {
        Item item = new Item(
                null,
                pedido,
                produto,
                quantidade,
                produto.getPreco()
        );
        itemRepository.save(item);
    }
}

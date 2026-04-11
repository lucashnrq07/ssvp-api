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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
    public void addItem(Long pedidoId, Long produtoId, Integer quantidade, Long assistidoId) {
        Pedido pedido = buscarPedido(pedidoId);
        validarDonoPedido(pedido, assistidoId);

        Produto produto = buscarProduto(produtoId);

        Optional<Item> itemExistente = buscarItemExistente(pedidoId, produtoId);

        if (itemExistente.isPresent()) {
            atualizarQuantidade(itemExistente.get(), quantidade);
        } else {
            criarNovoItem(pedido, produto, quantidade);
        }
    }

    // REMOVER ITEM
    public void removerItem(Long pedidoId, Long produtoId, Long assistidoId) {
        Pedido pedido = buscarPedido(pedidoId);
        validarDonoPedido(pedido, assistidoId);

        Item item = itemRepository.buscarItem(pedidoId, produtoId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        itemRepository.delete(item);
    }

    // FINALIZAR PEDIDO
    public PedidoDTO finalizarPedido(Long pedidoId, Long assistidoId) {
        Pedido pedido = buscarPedido(pedidoId);
        validarDonoPedido(pedido, assistidoId);

        validarPedidoPodeSerFinalizado(pedido);

        List<Item> itens = buscarItensDoPedido(pedidoId);

        BigDecimal total = calcularTotal(itens);

        debitarSaldo(pedido.getAssistido(), total);

        atualizarStatus(pedido);

        repository.save(pedido);

        return toDTO(pedido);
    }

    // ===== MÉTODOS AUXILIARES =====

    private void validarDonoPedido(Pedido pedido, Long assistidoId) {
        if (!pedido.getAssistido().getId().equals(assistidoId)) {
            throw new RuntimeException("Acesso negado");
        }
    }

    private Assistido buscarAssistido(Long id) {
        return assistidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assistido não encontrado"));
    }

    private void validarPedidoNoMes(Long assistidoId, int mes) {
        if (repository.existsPedidoNoMes(assistidoId, mes)) {
            throw new RuntimeException("Já existe pedido para esse mês");
        }
    }

    private Pedido criarPedidoEntity(Assistido assistido, int mes, int ano) {
        return new Pedido(
                null,
                assistido,
                Status.PEDIDO_CRIADO,
                mes,
                ano,
                LocalDate.now()
        );
    }

    private PedidoDTO toDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getAssistido().getId(),
                pedido.getStatus(),
                pedido.getMesReferencia(),
                pedido.getAnoReferencia(),
                pedido.getDataCriacao()
        );
    }

    private Pedido buscarPedido(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    private Produto buscarProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    private Optional<Item> buscarItemExistente(Long pedidoId, Long produtoId) {
        return itemRepository.buscarItem(pedidoId, produtoId);
    }

    private void atualizarQuantidade(Item item, Integer quantidade) {
        item.setQuantidade(item.getQuantidade() + quantidade);
        itemRepository.save(item);
    }

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

    private void validarPedidoPodeSerFinalizado(Pedido pedido) {
        if (pedido.getStatus() != Status.PEDIDO_CRIADO) {
            throw new RuntimeException("Pedido já realizado");
        }
    }

    private List<Item> buscarItensDoPedido(Long pedidoId) {
        List<Item> itens = itemRepository.findByPedidoId(pedidoId);

        if (itens.isEmpty()) {
            throw new RuntimeException("Pedido sem itens");
        }

        return itens;
    }

    private BigDecimal calcularTotal(List<Item> itens) {
        return itens.stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void debitarSaldo(Assistido assistido, BigDecimal total) {
        if (assistido.getSaldo().compareTo(total) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        assistido.setSaldo(assistido.getSaldo().subtract(total));
    }

    private void atualizarStatus(Pedido pedido) {
        pedido.setStatus(Status.AGUARDANDO_SEPARACAO);
    }
}
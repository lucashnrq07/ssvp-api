package com.lucas.ssvp_api.services;

import com.lucas.ssvp_api.dto.PedidoDTO;
import com.lucas.ssvp_api.model.entities.Assistido;
import com.lucas.ssvp_api.model.entities.Pedido;
import com.lucas.ssvp_api.model.enums.Status;
import com.lucas.ssvp_api.repositories.AssistidoRepository;
import com.lucas.ssvp_api.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final AssistidoRepository assistidoRepository;

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


    // MÉTODOS AUXILIARES

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
}

package com.lucas.ssvp_api.dto;

import com.lucas.ssvp_api.model.enums.Status;

import java.time.LocalDate;

public record PedidoDTO(
        Long assistidoId,
        Status status,
        Integer mes,
        Integer ano,
        LocalDate data
) {
}

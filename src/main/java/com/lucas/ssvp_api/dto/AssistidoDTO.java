package com.lucas.ssvp_api.dto;

import java.math.BigDecimal;

public record AssistidoDTO(
        String nome,
        String cpf,
        BigDecimal saldo,
        Long conferenciaId
) {
}

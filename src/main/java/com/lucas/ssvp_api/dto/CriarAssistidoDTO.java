package com.lucas.ssvp_api.dto;

import java.math.BigDecimal;

public record CriarAssistidoDTO(
        String nome,
        String cpf,
        String pin,
        Long conferenciaId
) {
}

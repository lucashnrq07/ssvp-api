package com.lucas.ssvp_api.dto;

import java.math.BigDecimal;

public record ProdutoDTO(
        String nome,
        BigDecimal preco
) {
}

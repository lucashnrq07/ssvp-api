package com.lucas.ssvp_api.repositories;

import com.lucas.ssvp_api.model.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

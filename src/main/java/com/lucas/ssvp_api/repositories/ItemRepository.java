package com.lucas.ssvp_api.repositories;

import com.lucas.ssvp_api.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
    SELECT i FROM Item i
    WHERE i.pedido.id = :pedidoId
    AND i.produto.id = :produtoId
    """)
    Optional<Item> buscarItem(Long pedidoId, Long produtoId);
}

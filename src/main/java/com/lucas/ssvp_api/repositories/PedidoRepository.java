package com.lucas.ssvp_api.repositories;

import com.lucas.ssvp_api.model.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

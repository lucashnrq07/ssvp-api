package com.lucas.ssvp_api.repositories;

import com.lucas.ssvp_api.model.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("""
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM Pedido p
    WHERE p.assistido.id = :assistidoId
    AND p.mesReferencia = :mes
    """)
    boolean existsPedidoNoMes(Long assistidoId, int mes);
}

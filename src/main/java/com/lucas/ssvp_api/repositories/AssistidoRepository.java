package com.lucas.ssvp_api.repositories;

import com.lucas.ssvp_api.model.entities.Assistido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssistidoRepository extends JpaRepository<Assistido, Long> {

    Optional<Assistido> findByCpf(String cpf);
}

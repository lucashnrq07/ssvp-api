package com.lucas.ssvp_api.repositories;

import com.lucas.ssvp_api.model.entities.Conferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenciaRepository extends JpaRepository<Conferencia, Long> {
}

package com.lucas.ssvp_api.repositories;

import com.lucas.ssvp_api.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

package com.lucas.ssvp_api.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "vicentinos")
public class Vicentino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "conselho_particular_id", unique = true, nullable = false)
    private ConselhoParticular conselhoParticular;

    @Column(nullable = false)
    private String senha;
}

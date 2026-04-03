package com.lucas.ssvp_api.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "conferencias")
public class Conferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conselho_particular_id")
    private ConselhoParticular conselhoParticular;
}

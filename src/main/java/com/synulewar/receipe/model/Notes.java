package com.synulewar.receipe.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "recipe")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;

    @Lob //JPA knows it will be large
    private String recipeNotes;

}

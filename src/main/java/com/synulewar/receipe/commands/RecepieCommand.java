package com.synulewar.receipe.commands;

import com.synulewar.receipe.model.Difficulty;
import com.synulewar.receipe.model.Ingredient;
import com.synulewar.receipe.model.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class RecepieCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private Set<CategoryCommand> categories = new HashSet<>();
    private NotesCommand notes = new NotesCommand();

}


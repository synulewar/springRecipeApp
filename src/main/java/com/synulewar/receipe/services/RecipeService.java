package com.synulewar.receipe.services;

import com.synulewar.receipe.commands.RecepieCommand;
import com.synulewar.receipe.model.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipeList();
    Recipe findById(Long id);
    RecepieCommand saveRecepieCommand(RecepieCommand recepieCommand);
}

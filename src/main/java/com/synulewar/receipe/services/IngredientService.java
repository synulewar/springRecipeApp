package com.synulewar.receipe.services;

import com.synulewar.receipe.commands.IngredientCommand;
import com.synulewar.receipe.model.Ingredient;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}

package com.synulewar.receipe.services;

import com.synulewar.receipe.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipeList();
}

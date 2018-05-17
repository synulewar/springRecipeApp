package com.synulewar.receipe.services;

import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.repositories.RecipieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipieRepository recipieRepository;

    public RecipeServiceImpl(RecipieRepository recipieRepository) {
        this.recipieRepository = recipieRepository;
    }

    public Set<Recipe> getRecipeList() {
        Set<Recipe> recipes = new HashSet<>();
        recipieRepository.findAll().forEach(recipes::add);
        return recipes;
    }
}

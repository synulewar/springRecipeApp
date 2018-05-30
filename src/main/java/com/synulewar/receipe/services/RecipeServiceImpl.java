package com.synulewar.receipe.services;

import com.synulewar.receipe.commands.RecepieCommand;
import com.synulewar.receipe.converters.RecipeCommandToRecipe;
import com.synulewar.receipe.converters.RecipeToRecipeCommand;
import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.repositories.RecipieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipieRepository recipieRepository;
    private RecipeCommandToRecipe recipeCommandToRecipe;
    private RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipieRepository recipieRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipieRepository = recipieRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    public Set<Recipe> getRecipeList() {
        log.debug("I m in getRecipeList");
        Set<Recipe> recipes = new HashSet<>();
        recipieRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> recipeOptional = recipieRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe didn found");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecepieCommand saveRecepieCommand(RecepieCommand recepieCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recepieCommand);
        Recipe savedReciep = recipieRepository.save(detachedRecipe);
        log.debug("Saved recipe " + savedReciep.getId());
        return recipeToRecipeCommand.convert(savedReciep);
    }
}

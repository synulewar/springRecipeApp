package com.synulewar.receipe.services;

import com.synulewar.receipe.commands.IngredientCommand;
import com.synulewar.receipe.converters.IngredientToIngredientCommand;
import com.synulewar.receipe.model.Ingredient;
import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.repositories.RecipieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private RecipieRepository recipieRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipieRepository recipieRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipieRepository = recipieRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipieRepository.findById(recipeId);
        if (!optionalRecipe.isPresent()) {
            log.error("AAA there is no recipe!");
        }

        Recipe recipe = optionalRecipe.get();
        Optional<IngredientCommand> ingredientCommand = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        return ingredientCommand.orElseGet(IngredientCommand::new);
    }
}

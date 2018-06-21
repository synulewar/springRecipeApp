package com.synulewar.receipe.services;

import com.synulewar.receipe.commands.IngredientCommand;
import com.synulewar.receipe.commands.RecepieCommand;
import com.synulewar.receipe.converters.IngredientCommandToIngredient;
import com.synulewar.receipe.converters.IngredientToIngredientCommand;
import com.synulewar.receipe.model.Ingredient;
import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.repositories.RecipieRepository;
import com.synulewar.receipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private RecipieRepository recipieRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private UnitOfMeasureRepository unitOfMeasureRepository;


    public IngredientServiceImpl(RecipieRepository recipieRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipieRepository = recipieRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipieRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found");
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.
                        findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found")));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe recipeSaved = recipieRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = recipeSaved.getIngredients().stream()
                    .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = recipeSaved.getIngredients().stream()
                        .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();

            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteIngredientById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipieRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe was not found!");
        }

        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientToDelete = recipe.getIngredients()
                .stream()
                .filter(ingedient -> ingedient.getId().equals(ingredientId))
                .findFirst();

        if (!ingredientToDelete.isPresent()) {
            throw new RuntimeException("There is no ingredient to delete!");
        }

        Ingredient ingredient = ingredientToDelete.get();

        log.debug("Before remove");
        for (Ingredient ingr : recipe.getIngredients()) {
            log.debug(ingr.getDescription());
        }
        //have to get rid of recipe -- ingredient connection to make it disappear
        ingredient.setRecipe(null);
        recipe.removeIngredient(ingredientToDelete.get());
        recipieRepository.save(recipe);

        log.debug("After remove");
        for (Ingredient ingr : recipe.getIngredients()) {
            log.debug(ingr.getDescription());
        }

    }
}

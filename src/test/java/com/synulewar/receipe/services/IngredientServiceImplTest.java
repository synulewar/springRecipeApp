package com.synulewar.receipe.services;

import com.synulewar.receipe.commands.IngredientCommand;
import com.synulewar.receipe.converters.IngredientCommandToIngredient;
import com.synulewar.receipe.converters.IngredientToIngredientCommand;
import com.synulewar.receipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.synulewar.receipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.synulewar.receipe.model.Ingredient;
import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.model.UnitOfMeasure;
import com.synulewar.receipe.repositories.RecipieRepository;
import com.synulewar.receipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @Mock
    RecipieRepository recipieRepository;


    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientServiceImpl ingredientService;


    public IngredientServiceImplTest() {
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipieRepository, ingredientToIngredientCommand,
                ingredientCommandToIngredient, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient1.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient1.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipieRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        verify(recipieRepository, times(1)).findById(anyLong());


    }

    @Test
    public void saveIngredientCommand() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        savedRecipe.addIngredient(ingredient);


        when(recipieRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipieRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipieRepository, times(1)).findById(anyLong());
        verify(recipieRepository, times(1)).save(any(Recipe.class));



    }
}
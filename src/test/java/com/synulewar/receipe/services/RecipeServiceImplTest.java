package com.synulewar.receipe.services;

import com.synulewar.receipe.converters.RecipeCommandToRecipe;
import com.synulewar.receipe.converters.RecipeToRecipeCommand;
import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.repositories.RecipieRepository;
import exceptions.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipieRepository recipieRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipieRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }


    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipieRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);
        assertNotNull("We get null", recipeReturned);
        verify(recipieRepository, times((1))).findById(anyLong());
        verify(recipieRepository, never()).findAll();
    }

    @Test
    public void getRecipeList() {

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeHashSet = new HashSet<>();
        recipeHashSet.add(recipe);

        //Tak sie mokuje w mochito
        when(recipieRepository.findAll()).thenReturn(recipeHashSet);
        Set<Recipe> recipes = recipeService.getRecipeList();
        assertEquals(1, recipes.size());

        verify(recipieRepository, times(1)).findAll();

    }

    @Test
    public void testDeleteById() {
        Long idTodelete = Long.valueOf(2L);
        recipeService.deleteById(idTodelete);
        verify(recipieRepository, times(1)).deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() throws Exception {
        Optional<Recipe> optionalRecipe = Optional.empty();
        when(recipieRepository.findById(anyLong())).thenReturn(optionalRecipe);
        Recipe recipe = recipeService.findById(1L);
    }
}
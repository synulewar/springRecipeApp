package com.synulewar.receipe.services;

import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.repositories.RecipieRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipieRepository recipieRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipieRepository);
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
}
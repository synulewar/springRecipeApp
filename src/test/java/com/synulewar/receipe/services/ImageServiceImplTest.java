package com.synulewar.receipe.services;

import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.repositories.RecipieRepository;
import exceptions.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceImplTest {

    @Mock
    RecipieRepository recipieRepository;

    ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipieRepository);
    }

    @Test
    public void saveImageFile() throws Exception {
        Long id = 1L;
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Krzys".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> optionalReciep = Optional.of(recipe);
        when(recipieRepository.findById(anyLong())).thenReturn(optionalReciep);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(id, multipartFile);

        verify(recipieRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}
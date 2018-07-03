package com.synulewar.receipe.controllers;

import com.synulewar.receipe.commands.RecepieCommand;
import com.synulewar.receipe.services.ImageService;
import com.synulewar.receipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {



    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.
                standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

    }

    @Test
    public void getImageFormGet() throws Exception {
        RecepieCommand recepieCommand = new RecepieCommand();
        recepieCommand.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recepieCommand);

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }


    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Krzys".getBytes());


        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void renderImageFromDb() throws Exception {
        RecepieCommand recepieCommand = new RecepieCommand();
        recepieCommand.setId(1L);

        String s = "fake image text";
        Byte[] bytes = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte: s.getBytes()) {
            bytes[i++] = primByte;
        }

        recepieCommand.setImage(bytes);
        when(recipeService.findCommandById(anyLong())).thenReturn(recepieCommand);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, responseBytes.length);
    }

    @Test
    public void testimageNumberFormatException() throws Exception {
        mockMvc.perform(get("/recipe/pki/recipeimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));

    }

}
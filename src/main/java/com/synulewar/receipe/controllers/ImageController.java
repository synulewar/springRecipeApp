package com.synulewar.receipe.controllers;

import com.synulewar.receipe.services.ImageService;
import com.synulewar.receipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class ImageController {
    private ImageService mImageService;
    private RecipeService mRecipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.mImageService = imageService;
        this.mRecipeService = recipeService;
    }

    @GetMapping("recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", mRecipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/imageuploadform";
    }


    @PostMapping("recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile multipartFile) {
        mImageService.saveImageFile(Long.valueOf(recipeId), multipartFile);
        return "redirect:/recipe/" + recipeId + "/show";
    }





}

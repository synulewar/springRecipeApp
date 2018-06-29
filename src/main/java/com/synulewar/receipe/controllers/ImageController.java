package com.synulewar.receipe.controllers;

import com.synulewar.receipe.commands.RecepieCommand;
import com.synulewar.receipe.services.ImageService;
import com.synulewar.receipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    //we dont request web but HTTP response
    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void renderImageFromDb(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecepieCommand recepieCommand = mRecipeService.findCommandById(Long.valueOf(recipeId));

        if (recepieCommand.getImage() == null) {
            return;
        }

        byte[] byteArray = new byte[recepieCommand.getImage().length];
        int i = 0;
        for (Byte b: recepieCommand.getImage()) {
            byteArray[i++] = b;
        }
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());
    }






}

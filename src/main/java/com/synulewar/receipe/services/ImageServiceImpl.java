package com.synulewar.receipe.services;

import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.repositories.RecipieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipieRepository recipieRepository;

    @Autowired
    public ImageServiceImpl(RecipieRepository recipieRepository) {
        this.recipieRepository = recipieRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {

        try {
            Optional<Recipe> optionalRecipe = recipieRepository.findById(id);
            if (!optionalRecipe.isPresent()) {
                throw new RuntimeException("Recie is missing");
            }
            Byte[] bytes = new Byte[file.getBytes().length];
            byte[] biciki = file.getBytes();
            for (int i = 0; i < file.getBytes().length; i++) {
                bytes[i] = biciki[i];
            }
            Recipe recipe = optionalRecipe.get();
            recipe.setImage(bytes);
            recipieRepository.save(recipe);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

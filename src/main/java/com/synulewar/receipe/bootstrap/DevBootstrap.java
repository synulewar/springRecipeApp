package com.synulewar.receipe.bootstrap;

import com.synulewar.receipe.model.*;
import com.synulewar.receipe.repositories.CategoryRepository;
import com.synulewar.receipe.repositories.RecipieRepository;
import com.synulewar.receipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sun.applet.AppletListener;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipieRepository recipieRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public DevBootstrap(CategoryRepository categoryRepository, RecipieRepository recipieRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipieRepository = recipieRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private void initData() {
        List<Recipe> recipes = new ArrayList<>();

        //Unit of measure
        Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByUom("Teaspoon");

        if (!teaspoonOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        Optional<UnitOfMeasure> tablespoonOptional = unitOfMeasureRepository.findByUom("Teaspoon");

        if (!tablespoonOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByUom("Cup");

        if (!cupOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        Optional<UnitOfMeasure> pinchOptional = unitOfMeasureRepository.findByUom("Pinch");

        if (!pinchOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        Optional<UnitOfMeasure> ounceOptional = unitOfMeasureRepository.findByUom("Ounce");

        if (!ounceOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        Optional<UnitOfMeasure> ripeOptional = unitOfMeasureRepository.findByUom("Ripe");

        if (!ripeOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        Optional<UnitOfMeasure> dashOptional = unitOfMeasureRepository.findByUom("Dash");

        if (!dashOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        Optional<UnitOfMeasure> cloveOptional = unitOfMeasureRepository.findByUom("Clove");

        if (!cloveOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        Optional<UnitOfMeasure> eachOptional = unitOfMeasureRepository.findByUom("Each");

        if (!eachOptional.isPresent()) {
            throw new RuntimeException("Expected uom not found!");
        }

        //Categories
        Optional<Category> americanOptional = categoryRepository.findByDescription("American");

        if (!americanOptional.isPresent()) {
            throw new RuntimeException("Expected category not found!");
        }


        Optional<Category> italianOptional = categoryRepository.findByDescription("Italian");

        if (!italianOptional.isPresent()) {
            throw new RuntimeException("Expected category not found!");
        }

        Optional<Category> mexicanOptional = categoryRepository.findByDescription("Mexican");

        if (!mexicanOptional.isPresent()) {
            throw new RuntimeException("Expected category not found!");
        }

        Optional<Category> fastFoodOptional = categoryRepository.findByDescription("Fast Food");

        if (!fastFoodOptional.isPresent()) {
            throw new RuntimeException("Expected category not found!");
        }

        UnitOfMeasure teaspoon = teaspoonOptional.get();
        UnitOfMeasure tablespoon = tablespoonOptional.get();
        UnitOfMeasure cup = cupOptional.get();
        UnitOfMeasure pinch = pinchOptional.get();
        UnitOfMeasure ounce = ounceOptional.get();
        UnitOfMeasure dash = dashOptional.get();
        UnitOfMeasure clove = cloveOptional.get();
        UnitOfMeasure each = eachOptional.get();

        Category american = americanOptional.get();
        Category mexican = mexicanOptional.get();
        Category fastFood  = fastFoodOptional.get();
        Category italian = italianOptional.get();



        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside " +
                "of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.)" +
                " Place in a bowl.\n" + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it!" +
                " The guacamole should be a little chunky.)\n" + "3 Add salt, lime juice, and the rest: Sprinkle with " +
                "salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of" +
                " the avocado and will help delay the avocados from turning brown.\n");
        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.");
        guacamole.setNotes(guacNotes);


        guacamole.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), each));
        guacamole.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon));
        guacamole.addIngredient(new Ingredient("fresh lime juice", new BigDecimal(2), tablespoon));
        guacamole.addIngredient(new Ingredient("blac peper", new BigDecimal(2), dash));


        guacamole.getCategories().add(american);
        guacamole.getCategories().add(mexican);

        recipieRepository.save(guacamole);

        Recipe grilledChicken = new Recipe();
        grilledChicken.setDescription("Spicy grilled Tacos");
        grilledChicken.setPrepTime(20);
        grilledChicken.setCookTime(5);
        grilledChicken.setDifficulty(Difficulty.MEDIUM);
        grilledChicken.setDirections("1. Random step i just have to type anythin\n2. Second part of miningless decription\n");
        Notes grilledNotes = new Notes();
        grilledNotes.setRecipeNotes("Dont do it better to cook with vapour");
        grilledChicken.setNotes(grilledNotes);
        grilledChicken.addIngredient(new Ingredient("Chicken", new BigDecimal(1), each));
        grilledChicken.addIngredient(new Ingredient("Garlic", new BigDecimal(2), teaspoon));
        grilledChicken.getCategories().add(fastFood);
        grilledChicken.getCategories().add(italian);
        recipieRepository.save(grilledChicken);

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("Enter bootstrap");
        initData();
    }
}

package com.synulewar.receipe.bootstrap;

import com.synulewar.receipe.model.Recipe;
import com.synulewar.receipe.model.UnitOfMeasure;
import com.synulewar.receipe.repositories.CategoryRepository;
import com.synulewar.receipe.repositories.RecipieRepository;
import com.synulewar.receipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sun.applet.AppletListener;
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
        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        recipieRepository.save(guacamole);

        Recipe grilledChicken = new Recipe();
        grilledChicken.setDescription("Spicy grilled Tacos");
        recipieRepository.save(grilledChicken);

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("Enter bootstrap");
        initData();
    }
}

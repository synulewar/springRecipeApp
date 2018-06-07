package com.synulewar.receipe.controllers;

import com.synulewar.receipe.commands.RecepieCommand;
import com.synulewar.receipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping("recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newReceipe(Model model) {
        model.addAttribute("recipe", new RecepieCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }


    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecepieCommand command) {
        RecepieCommand savedCommand = recipeService.saveRecepieCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show/";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}

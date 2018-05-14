package com.synulewar.receipe.repositories;

import com.synulewar.receipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipieRepository extends CrudRepository<Recipe, Long> {
}

package com.synulewar.receipe.repositories;

import com.synulewar.receipe.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}

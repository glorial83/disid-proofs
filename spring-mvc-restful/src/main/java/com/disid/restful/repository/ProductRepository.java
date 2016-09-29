package com.disid.restful.repository;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

@RooJpaRepository(entity = Product.class)
public interface ProductRepository {

  long countByCategoriesContains(Category category);

}

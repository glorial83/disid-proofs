package com.disid.restful.repository;

import java.util.Set;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;

@RooJpaRepository(entity = Product.class)
public interface ProductRepository {

  long countByCategoriesContains(Category category);

  Set<Product> findByIdIn(Long[] productIds);
}

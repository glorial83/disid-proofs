package com.disid.restful.repository;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = Product.class, defaultSearchResult = Product.class)
public interface ProductRepositoryCustom {

  Page<Product> findAllByCategories(Category category, GlobalSearch search,
      Pageable pageable);

}

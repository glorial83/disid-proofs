package com.disid.restful.service.api;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.repository.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooService;

@RooService(entity = Category.class)
public interface CategoryService {

    void delete(Category category);

    Category addToProducts(Category category, Long... products);

    Category addToProducts(Category category, Product... products);

    Category deleteFromProducts(Category category, Long... products);

    Category deleteFromProducts(Category category, Product... products);

    Page<Category> findAllByProduct(Product product, GlobalSearch search, Pageable pageable);

    long countByProductsContains(Product product);

  Product addToProducts(Product product, Long... categories);
    
  Product deleteFromProducts(Product product, Long... categories);
}

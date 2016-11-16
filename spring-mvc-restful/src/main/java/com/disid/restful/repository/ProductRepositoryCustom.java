package com.disid.restful.repository;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.model.ProductByNameAndDescriptionSearchForm;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

  Page<Product> findByCategoriesContains(Category category, GlobalSearch search, Pageable pageable);

  Page<Product> findAll(GlobalSearch globalSearch, Pageable pageable);

  long countByNameAndDescription(ProductByNameAndDescriptionSearchForm formBean);

  Page<Product> findByNameAndDescription(ProductByNameAndDescriptionSearchForm formBean,
      GlobalSearch search, Pageable pageable);

}

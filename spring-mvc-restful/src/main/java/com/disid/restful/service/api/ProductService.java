package com.disid.restful.service.api;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.repository.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooService;

import java.util.List;

@RooService(entity = Product.class)
public interface ProductService {

  void delete(Product product);

  Page<Product> findAllByCategory(Category category, GlobalSearch search, Pageable pageable);

  long countByCategoriesContains(Category category);

  List<Product> findAll(Long[] productIds);

}

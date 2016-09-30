package com.disid.restful.service.api;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.repository.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

  void delete(Product product);

  Page<Product> findAllByCategory(Category category, GlobalSearch search, Pageable pageable);

  long countByCategoriesContains(Category category);

  List<Product> findAll(Long[] productIds);

  Product save(Product entity);

  void delete(Long id);

  List<Product> save(Iterable<Product> entities);

  void delete(Iterable<Long> ids);

  List<Product> findAll();

  List<Product> findAll(Iterable<Long> ids);

  Product findOne(Long id);

  long count();

  Page<Product> findAll(GlobalSearch globalSearch, Pageable pageable);
}

package com.disid.restful.service.api;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;

import io.springlets.data.jpa.repository.support.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

  void delete(Category category);

  Category addToProducts(Category category, Long... products);

  Category addToProducts(Category category, Product... products);

  Category deleteFromProducts(Category category, Long... products);

  Category deleteFromProducts(Category category, Product... products);

  Product addToProducts(Product product, Long... categories);

  Product deleteFromProducts(Product product, Long... categories);

  List<Category> findAll(Long... categories);

  List<Category> findAll();

  List<Category> findAll(Iterable<Long> ids);

  Category findOne(Long id);

  long count();

  Page<Category> findAll(GlobalSearch globalSearch, Pageable pageable);

  Category save(Category entity);

  void delete(Long id);

  List<Category> save(Iterable<Category> entities);

  void delete(Iterable<Long> ids);

}

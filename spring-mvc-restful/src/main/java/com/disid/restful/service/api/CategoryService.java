package com.disid.restful.service.api;

import com.disid.restful.model.Category;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

  Category addToProducts(Category category, Iterable<Long> products);

  long count();

  void delete(Category category);

  void delete(Iterable<Long> ids);

  void delete(Long id);

  List<Category> findAll();

  Page<Category> findAll(GlobalSearch globalSearch, Pageable pageable);

  List<Category> findAll(Iterable<Long> ids);

  Category findOne(Long id);

  Category removeFromProducts(Category category, Iterable<Long> products);

  Category save(Category entity);

  List<Category> save(Iterable<Category> entities);

}

package com.disid.restful.service.impl;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.repository.CategoryRepository;
import com.disid.restful.service.api.CategoryService;
import com.disid.restful.service.api.ProductService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

  private ProductService productService;
  private CategoryRepository categoryRepository;

  private CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository,
      @Lazy ProductService productService) {
    this(categoryRepository);
    this.productService = productService;
  }

  @Transactional
  public void delete(Category category) {
    for (Product product : category.getProducts()) {
      product.getCategories().remove(category);
    }
    categoryRepository.delete(category);
  }

  @Transactional
  public Category addToProducts(Category category, Iterable<Long> productIds) {
    List<Product> products = productService.findAll(productIds);
    category.addToProducts(products);
    return categoryRepository.save(category);
  }

  @Transactional
  public Category removeFromProducts(Category category, Iterable<Long> productIds) {
    List<Product> products = productService.findAll(productIds);
    category.removeFromProducts(products);
    return categoryRepository.save(category);
  }

  @Transactional(readOnly = false)
  public Category save(Category entity) {
    return categoryRepository.save(entity);
  }

  @Transactional(readOnly = false)
  public void delete(Long id) {
    categoryRepository.delete(id);
  }

  @Transactional(readOnly = false)
  public List<Category> save(Iterable<Category> entities) {
    return categoryRepository.save(entities);
  }

  @Transactional(readOnly = false)
  public void delete(Iterable<Long> ids) {
    List<Category> toDelete = categoryRepository.findAll(ids);
    categoryRepository.deleteInBatch(toDelete);
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public List<Category> findAll(Iterable<Long> ids) {
    return categoryRepository.findAll(ids);
  }

  public Category findOne(Long id) {
    return categoryRepository.findOne(id);
  }

  public long count() {
    return categoryRepository.count();
  }

  public Page<Category> findAll(GlobalSearch globalSearch, Pageable pageable) {
    return categoryRepository.findAll(globalSearch, pageable);
  }
}

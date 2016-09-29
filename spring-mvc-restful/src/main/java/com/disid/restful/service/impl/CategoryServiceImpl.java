package com.disid.restful.service.impl;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.repository.CategoryRepository;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CategoryService;
import com.disid.restful.service.api.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RooServiceImpl(service = CategoryService.class)
public class CategoryServiceImpl {

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
  public Category addToProducts(Category category, Long... productIds) {
    List<Product> products = productService.findAll(productIds);
    category.addToProducts(products);
    return categoryRepository.save(category);
  }

  @Transactional
  public Category addToProducts(Category category, Product... products) {
    category.addToProducts(Arrays.asList(products));
    return categoryRepository.save(category);
  }

  @Transactional
  public Category deleteFromProducts(Category category, Long... productIds) {
    List<Product> products = productService.findAll(productIds);
    category.removeFromProducts(products);
    return categoryRepository.save(category);
  }

  @Transactional
  public Category deleteFromProducts(Category category, Product... products) {
    category.removeFromProducts(Arrays.asList(products));
    return categoryRepository.save(category);
  }

  public Page<Category> findAllByProduct(Product product, GlobalSearch search, Pageable pageable) {
    return categoryRepository.findAllByProducts(product, search, pageable);
  }

  public long countByProductsContains(Product product) {
    return categoryRepository.countByProductsContains(product);
  }

  @Transactional
  public Product addToProducts(Product product, Long... categories) {
    List<Category> categoryEntities = findAll(categories);
    for (Category category : categoryEntities) {
      addToProducts(category, product);
    }
    return product;
  }

  @Transactional
  public Product deleteFromProducts(Product product, Long... categories) {
    List<Category> categoryEntities = findAll(categories);
    for (Category category : categoryEntities) {
      deleteFromProducts(category, product);
    }
    return product;
  }

  public List<Category> findAll(Long... categories) {
    return categoryRepository.findAll(Arrays.asList(categories));
  }

}

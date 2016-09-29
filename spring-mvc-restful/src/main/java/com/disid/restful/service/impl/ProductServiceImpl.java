package com.disid.restful.service.impl;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.repository.ProductRepository;
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

@RooServiceImpl(service = ProductService.class)
public class ProductServiceImpl {

  private CategoryService categoryService;

  private ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository,
      @Lazy CategoryService categoryService) {
    this(productRepository);
    this.categoryService = categoryService;
  }

  @Transactional
  public void delete(Product product) {
    for (Category category : product.getCategories()) {
      categoryService.deleteFromProducts(category, product);
      categoryService.save(category);
    }
    productRepository.delete(product);
  }

  public Page<Product> findAllByCategory(Category category, GlobalSearch search,
      Pageable pageable) {
    return productRepository.findAllByCategories(category, search, pageable);
  }

  public long countByCategoriesContains(Category category) {
    return productRepository.countByCategoriesContains(category);
  }

  public List<Product> findAll(Long[] productIds) {
    return productRepository.findAll(Arrays.asList(productIds));
  }

}

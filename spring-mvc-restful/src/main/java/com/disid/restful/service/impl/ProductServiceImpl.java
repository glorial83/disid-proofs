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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

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


  public ProductRepository productRepository;

  @Transactional(readOnly = false)
  public Product save(Product entity) {
    return productRepository.save(entity);
  }

  @Transactional(readOnly = false)
  public void delete(Long id) {
    productRepository.delete(id);
  }

  @Transactional(readOnly = false)
  public List<Product> save(Iterable<Product> entities) {
    return productRepository.save(entities);
  }

  @Transactional(readOnly = false)
  public void delete(Iterable<Long> ids) {
    List<Product> toDelete = productRepository.findAll(ids);
    productRepository.deleteInBatch(toDelete);
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public List<Product> findAll(Iterable<Long> ids) {
    return productRepository.findAll(ids);
  }

  public Product findOne(Long id) {
    return productRepository.findOne(id);
  }

  public long count() {
    return productRepository.count();
  }

  public Page<Product> findAll(GlobalSearch globalSearch, Pageable pageable) {
    return productRepository.findAll(globalSearch, pageable);
  }
}

package com.disid.restful.web.category.api;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.service.api.CategoryService;
import com.disid.restful.service.api.ProductService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories/{category}/products",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriesItemProductsRestController {

  public CategoryService categoryService;

  public ProductService productService;

  @Autowired
  public CategoriesItemProductsRestController(CategoryService categoryService,
      ProductService productService) {
    this.categoryService = categoryService;
    this.productService = productService;
  }

  @ModelAttribute
  public Category getCategory(@PathVariable("category") Long id) {
    return categoryService.findOne(id);
  }

  @GetMapping
  public ResponseEntity<Page<Product>> listProduct(@ModelAttribute Category category,
      GlobalSearch search, Pageable pageable) {
    Page<Product> products = productService.findAllByCategory(category, search, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(products);
  }

  @PostMapping
  public ResponseEntity<?> addToProducts(@ModelAttribute Category category,
      @RequestBody Long product) {
    categoryService.addToProducts(category, product);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> removeFromProducts(@ModelAttribute Category category,
      @RequestBody Long product) {
    categoryService.deleteFromProducts(category, product);
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/batch")
  public ResponseEntity<?> addToProducts(@ModelAttribute Category category,
      @RequestBody Long[] products) {
    categoryService.addToProducts(category, products);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/batch")
  public ResponseEntity<?> removeFromProducts(@ModelAttribute Category category,
      @RequestBody Long[] products) {
    categoryService.deleteFromProducts(category, products);
    return ResponseEntity.ok().build();
  }

}

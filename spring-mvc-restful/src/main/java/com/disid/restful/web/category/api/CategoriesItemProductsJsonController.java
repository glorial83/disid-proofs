package com.disid.restful.web.category.api;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.service.api.CategoryService;
import com.disid.restful.service.api.ProductService;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.web.NotFoundException;

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

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/categories/{category}/products",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriesItemProductsJsonController {

  public CategoryService categoryService;

  public ProductService productService;

  @Autowired
  public CategoriesItemProductsJsonController(CategoryService categoryService,
      ProductService productService) {
    this.categoryService = categoryService;
    this.productService = productService;
  }

  @ModelAttribute
  public Category getCategory(@PathVariable("category") Long id) {
    Category category = categoryService.findOne(id);
    if (category == null) {
      throw new NotFoundException("Category not found");
    }
    return category;
  }

  @GetMapping
  public ResponseEntity<Page<Product>> listProducts(@ModelAttribute Category category,
      GlobalSearch globalSearch, Pageable pageable) {
    Page<Product> products =
        productService.findByCategoriesContains(category, globalSearch, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(products);
  }

  @PostMapping
  public ResponseEntity<?> addToProducts(@ModelAttribute Category category,
      @RequestBody Long product) {
    categoryService.addToProducts(category, Collections.singleton(product));
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> removeFromProducts(@ModelAttribute Category category,
      @RequestBody Long product) {
    categoryService.removeFromProducts(category, Collections.singleton(product));
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/batch")
  public ResponseEntity<?> addToProducts(@ModelAttribute Category category,
      @RequestBody Iterable<Long> products) {
    categoryService.addToProducts(category, products);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/batch")
  public ResponseEntity<?> removeFromProducts(@ModelAttribute Category category,
      @RequestBody Iterable<Long> products) {
    categoryService.removeFromProducts(category, products);
    return ResponseEntity.ok().build();
  }

}

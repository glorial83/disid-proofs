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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Collections;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/categories/{category}", name = "CategoriesItemJsonController",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriesItemJsonController {

  public CategoryService categoryService;

  public ProductService productService;

  @Autowired
  public CategoriesItemJsonController(CategoryService categoryService,
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

  // Update Category

  @PutMapping(name = "update")
  public ResponseEntity<?> update(@ModelAttribute Category storedCategory,
      @Valid @RequestBody Category category, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    if (storedCategory == null) {
      return ResponseEntity.notFound().build();
    }

    category.setId(storedCategory.getId());
    categoryService.save(category);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(name = "delete")
  public ResponseEntity<?> delete(@ModelAttribute Category category) {
    categoryService.delete(category);
    return ResponseEntity.ok().build();
  }

  @GetMapping(name = "show")
  public ResponseEntity<?> show(@ModelAttribute Category category) {
    if (category == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.status(HttpStatus.FOUND).body(category);
  }

  public static UriComponents showURI(Category category) {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(CategoriesItemJsonController.class).show(category))
        .buildAndExpand(category.getId()).encode();
  }

  // Management of the "products" relationship

  @GetMapping(value = "/products", name = "listProducts")
  public ResponseEntity<Page<Product>> listProducts(@ModelAttribute Category category,
      GlobalSearch globalSearch, Pageable pageable) {
    Page<Product> products =
        productService.findByCategoriesContains(category, globalSearch, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(products);
  }

  @PostMapping(value = "/products", name = "addToProducts")
  public ResponseEntity<?> addToProducts(@ModelAttribute Category category,
      @RequestBody Long product) {
    categoryService.addToProducts(category, Collections.singleton(product));
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/products", name = "removeFromProducts")
  public ResponseEntity<?> removeFromProducts(@ModelAttribute Category category,
      @RequestBody Long product) {
    categoryService.removeFromProducts(category, Collections.singleton(product));
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/products/batch", name = "addToProductsBatch")
  public ResponseEntity<?> addToProductsBatch(@ModelAttribute Category category,
      @RequestBody Iterable<Long> products) {
    categoryService.addToProducts(category, products);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/products/batch", name = "removeFromProductsBatch")
  public ResponseEntity<?> removeFromProductsBatch(@ModelAttribute Category category,
      @RequestBody Iterable<Long> products) {
    categoryService.removeFromProducts(category, products);
    return ResponseEntity.ok().build();
  }

}

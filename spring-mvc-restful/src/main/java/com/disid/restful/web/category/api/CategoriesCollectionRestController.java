package com.disid.restful.web.category.api;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/categories", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriesCollectionRestController {

  public CategoryService categoryService;

  @Autowired
  public CategoriesCollectionRestController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // Create Categories

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Category category, BindingResult result) {
    if (category.getId() != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    Category newCategory = categoryService.save(category);

    URI uri = fromMethodCall(on(CategoriesItemRestController.class).show(null))
        .buildAndExpand(newCategory.getId()).encode().toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<Category>> list(Pageable pageable) {
    Page<Category> category = categoryService.findAll(null, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(category);
  }

  // Batch operations with Categories

  @PostMapping(value = "/batch")
  public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Category> categorys,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    categoryService.save(categorys);

    URI uri = fromMethodCall(on(getClass()).list(null)).build().encode().toUri();
    return ResponseEntity.created(uri).build();
  }

  @PutMapping(value = "/batch")
  public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Category> categorys,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    categoryService.save(categorys);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/batch/{ids}")
  public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    categoryService.delete(ids);
    return ResponseEntity.ok().build();
  }

}

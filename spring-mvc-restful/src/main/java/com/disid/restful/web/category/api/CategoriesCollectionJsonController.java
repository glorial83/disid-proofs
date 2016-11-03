package com.disid.restful.web.category.api;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import io.springlets.data.domain.GlobalSearch;

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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Collection;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/categories", name = "CategoriesCollectionJsonController",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriesCollectionJsonController {

  public CategoryService categoryService;

  @Autowired
  public CategoriesCollectionJsonController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // Create Categories

  @PostMapping(name = "create")
  public ResponseEntity<?> create(@Valid @RequestBody Category category, BindingResult result) {
    if (category.getId() != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    Category newCategory = categoryService.save(category);

    UriComponents showURI = CategoriesItemJsonController.showURI(newCategory);
    return ResponseEntity.created(showURI.toUri()).build();
  }

  @GetMapping(name = "list")
  public ResponseEntity<Page<Category>> list(GlobalSearch globalSearch, Pageable pageable) {
    Page<Category> category = categoryService.findAll(globalSearch, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(category);
  }

  /**
   * Returns the {@link UriComponents} to build the URI linked to this
   * controller's {@link #list(GlobalSearch, Pageable)} method.
   * @return the {@link UriComponents} for the URI to the list method 
   */
  public static UriComponents listURI() {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(CategoriesCollectionJsonController.class).list(null, null))
        .build()
        .encode();
  }

  // Batch operations with Categories

  @PostMapping(value = "/batch", name = "createBatch")
  public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Category> categorys,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    categoryService.save(categorys);

    return ResponseEntity.created(listURI().toUri()).build();
  }

  @PutMapping(value = "/batch", name = "updateBatch")
  public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Category> categorys,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    categoryService.save(categorys);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
  public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    categoryService.delete(ids);
    return ResponseEntity.ok().build();
  }

}

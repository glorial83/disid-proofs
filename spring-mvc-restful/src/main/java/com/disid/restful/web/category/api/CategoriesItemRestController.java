package com.disid.restful.web.category.api;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/categories/{category}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriesItemRestController {

  public CategoryService categoryService;

  @Autowired
  public CategoriesItemRestController(CategoryService categoryService) {
    this.categoryService = categoryService;
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

  @PutMapping
  public ResponseEntity<?> update(@ModelAttribute Category storedCategory,
      @Valid @RequestBody Category category, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    if (storedCategory == null) {
      return ResponseEntity.notFound().build();
    }

    category.setId(storedCategory.getId());
    category.setVersion(storedCategory.getVersion());
    categoryService.save(category);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@ModelAttribute Category category) {
    categoryService.delete(category);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<?> show(@ModelAttribute Category category) {
    if (category == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.status(HttpStatus.FOUND).body(category);
  }

  public static UriComponents showURI(Category category) {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(CategoriesItemRestController.class).show(category))
        .buildAndExpand(category.getId()).encode();
  }

}

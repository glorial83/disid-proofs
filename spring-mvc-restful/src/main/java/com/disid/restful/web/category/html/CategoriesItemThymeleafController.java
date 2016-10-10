package com.disid.restful.web.category.html;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/categories/{category}", produces = MediaType.TEXT_HTML_VALUE)
public class CategoriesItemThymeleafController {

  public CategoryService categoryService;

  @Autowired
  public CategoriesItemThymeleafController(CategoryService categoryService) {
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

  @GetMapping("/edit-form")
  public String editForm(@ModelAttribute Category category, Model model) {
    return "categories/edit";
  }

  @PutMapping
  public String update(@Valid @ModelAttribute Category category, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "categories/edit";
    }
    Category savedCategory = categoryService.save(category);
    UriComponents showURI = CategoriesItemThymeleafController.showURI(savedCategory);
    return "redirect:" + showURI.toUriString();
  }

  @DeleteMapping
  public String delete(@ModelAttribute Category category, Model model) {
    categoryService.delete(category);
    UriComponents listURI = CategoriesCollectionThymeleafController.listURI();
    return "redirect:" + listURI.toUriString();
  }

  @GetMapping
  public String show(@ModelAttribute Category category, Model model) {
    return "categories/show";
  }

  public static UriComponents showURI(Category category) {
    return fromMethodCall(on(CategoriesItemThymeleafController.class).show(category, null))
        .buildAndExpand(category.getId()).encode();
  }
}

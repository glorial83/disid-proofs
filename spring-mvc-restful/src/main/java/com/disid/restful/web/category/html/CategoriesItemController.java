package com.disid.restful.web.category.html;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

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

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/categories/{category}", produces = MediaType.TEXT_HTML_VALUE)
public class CategoriesItemController {

  public CategoryService categoryService;

  @Autowired
  public CategoriesItemController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @ModelAttribute
  public Category getCategory(@PathVariable("category") Long id) {
    return categoryService.findOne(id);
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
    redirectAttrs.addAttribute("id", savedCategory.getId());
    return "redirect:/categories/{id}";
  }

  @DeleteMapping
  public String delete(@ModelAttribute Category category, Model model) {
    categoryService.delete(category);
    return "redirect:/categories";
  }

  @GetMapping
  public String show(@ModelAttribute Category category, Model model) {
    return "categories/show";
  }

}

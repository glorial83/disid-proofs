package com.disid.restful.web.category;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories/{category}")
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

  // Update Category

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> update(@ModelAttribute Category storedCategory,
      @Valid @RequestBody Category category, BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity<BindingResult>(result, HttpStatus.CONFLICT);
    }

    if (storedCategory == null) {
      return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
    }

    storedCategory.setName(category.getName());
    storedCategory.setDescription(category.getDescription());
    Category savedCategory = categoryService.save(storedCategory);
    return new ResponseEntity<Category>(savedCategory, HttpStatus.OK);
  }

  @RequestMapping(value = "/edit-form", method = RequestMethod.GET,
      produces = MediaType.TEXT_HTML_VALUE)
  public String editForm(@ModelAttribute Category category, Model model) {
    return "categories/edit";
  }

  @RequestMapping(method = RequestMethod.PUT, produces = MediaType.TEXT_HTML_VALUE)
  public String update(@Valid @ModelAttribute Category category, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "categories/edit";
    }
    Category savedCategory = categoryService.save(category);
    redirectAttrs.addAttribute("id", savedCategory.getId());
    return "redirect:/categories/{id}";
  }

  // Delete category

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
  public String delete(@ModelAttribute Category category, Model model) {
    categoryService.delete(category);
    return "redirect:/categories";
  }

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Category> delete(@ModelAttribute Category category) {
    categoryService.delete(category);
    return new ResponseEntity<Category>(HttpStatus.OK);
  }

  // Show Category

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Category> show(@ModelAttribute Category category) {
    if (category == null) {
      return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Category>(category, HttpStatus.FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String show(@ModelAttribute Category category, Model model) {
    return "categories/show";
  }

}

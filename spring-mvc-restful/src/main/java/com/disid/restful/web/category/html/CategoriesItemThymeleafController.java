package com.disid.restful.web.category.html;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Locale;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/categories/{category}", name = "CategoriesItemThymeleafController",
    produces = MediaType.TEXT_HTML_VALUE)
public class CategoriesItemThymeleafController {

  public CategoryService categoryService;
  private MessageSource messageSource;

  @Autowired
  public CategoriesItemThymeleafController(CategoryService categoryService,
      MessageSource messageSource) {
    this.categoryService = categoryService;
    this.messageSource = messageSource;
  }

  @ModelAttribute
  public Category getCategory(@PathVariable("category") Long id, Locale locale) {
    Category category = categoryService.findOne(id);
    if (category == null) {
      String message = messageSource.getMessage("error_categoryNotFound", null, locale);
      throw new NotFoundException(message);
    }
    return category;
  }

  @GetMapping(value = "/edit-form", name = "editForm")
  public ModelAndView editForm(@ModelAttribute Category category, Model model) {
    return new ModelAndView("categories/edit");
  }

  @PutMapping(name = "update")
  public ModelAndView update(@Valid @ModelAttribute Category category, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      return new ModelAndView("categories/edit");
    }
    Category savedCategory = categoryService.save(category);
    UriComponents showURI = CategoriesItemThymeleafController.showURI(savedCategory);
    return new ModelAndView("redirect:" + showURI.toUriString());
  }

  // TODO: indicar que ya no se emplea por lo que no hace falta generarlo
  /*
  @DeleteMapping(name = "delete")
  public ModelAndView delete(@ModelAttribute Category category, Model model) {
    categoryService.delete(category);
    UriComponents listURI = CategoriesCollectionThymeleafController.listURI();
    return new ModelAndView("redirect:" + listURI.toUriString());
  }
  */

  @GetMapping(name = "show")
  public ModelAndView show(@ModelAttribute Category category, Model model) {
    return new ModelAndView("categories/show");
  }

  public static UriComponents showURI(Category category) {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(CategoriesItemThymeleafController.class).show(null, null))
        .buildAndExpand(category.getId()).encode();
  }
}

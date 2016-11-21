package com.disid.restful.web.category.html;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesData;
import io.springlets.data.web.datatables.DatatablesPageable;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/categories", name = "CategoriesCollectionThymeleafController",
    produces = MediaType.TEXT_HTML_VALUE)
public class CategoriesCollectionThymeleafController {

  private CategoryService categoryService;
  private MethodLinkBuilderFactory<CategoriesItemThymeleafController> itemLink;

  @Autowired
  public CategoriesCollectionThymeleafController(CategoryService categoryService,
      ControllerMethodLinkBuilderFactory linkBuilder) {
    this.categoryService = categoryService;
    this.itemLink = linkBuilder.of(CategoriesItemThymeleafController.class);
  }

  @InitBinder("category")
  public void initOwnerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  // Create Categories

  @GetMapping(value = "/create-form", name = "createForm")
  public ModelAndView createForm(Model model) {
    model.addAttribute(new Category());
    return new ModelAndView("categories/create");
  }

  @PostMapping(name = "create")
  public ModelAndView create(@Valid @ModelAttribute Category category, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      return new ModelAndView("categories/create");
    }
    Category newCategory = categoryService.save(category);

    UriComponents showURI = itemLink.to("show").with("category", newCategory.getId()).toUri();
    return new ModelAndView("redirect:" + showURI.toUriString());
  }

  // List Categories
  @GetMapping(name = "list")
  public ModelAndView list(Model model) {
    return new ModelAndView("categories/list");
  }

  @GetMapping(value = "/dt", name = "datatables", produces = Datatables.MEDIA_TYPE)
  public ResponseEntity<DatatablesData<Category>> datatables(GlobalSearch search,
      DatatablesPageable pageable, @RequestParam(Datatables.PARAMETER_DRAW) Integer draw) {
    Page<Category> categories = categoryService.findAll(search, pageable);
    long totalCategoriesCount = categories.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalCategoriesCount = categoryService.count();
    }
    DatatablesData<Category> datatablesData =
        new DatatablesData<Category>(categories, totalCategoriesCount, draw);
    return ResponseEntity.ok(datatablesData);
  }

}

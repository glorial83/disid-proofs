package com.disid.restful.web.category.html;

import com.disid.restful.datatables.Datatables;
import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.datatables.DatatablesPageable;
import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/categories", produces = MediaType.TEXT_HTML_VALUE)
public class CategoriesCollectionController {

  public CategoryService categoryService;

  @Autowired
  public CategoriesCollectionController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @InitBinder("category")
  public void initOwnerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  // Create Categories

  @GetMapping("/create-form")
  public String createForm(Model model) {
    model.addAttribute(new Category());
    return "categories/create";
  }

  @PostMapping
  public String create(@Valid @ModelAttribute Category category, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "categories/create";
    }
    Category newCategory = categoryService.save(category);
    redirectAttrs.addAttribute("id", newCategory.getId());
    return "redirect:/categories/{id}";
  }

  // List Categories
  @GetMapping
  public String list(Model model) {
    return "categories/list";
  }

  @GetMapping(produces = Datatables.MEDIA_TYPE)
  public ResponseEntity<DatatablesData<Category>> list(GlobalSearch search,
      DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
    Page<Category> categories = categoryService.findAll(search, pageable);
    long totalCategoriesCount = categories.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalCategoriesCount = categoryService.count();
    }
    DatatablesData<Category> datatablesData =
        new DatatablesData<Category>(categories, totalCategoriesCount, draw);
    return ResponseEntity.status(HttpStatus.FOUND).body(datatablesData);
  }

}

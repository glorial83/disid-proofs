package com.disid.restful.web.category.html;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.service.api.CategoryService;
import com.disid.restful.service.api.ProductService;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesData;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/categories/{category}/products",
    name = "CategoriesItemProductsThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class CategoriesItemProductsThymeleafController {

  public CategoryService categoryService;

  public ProductService productService;

  private MessageSource messageSource;

  private MethodLinkBuilderFactory<CategoriesCollectionThymeleafController> collectionLink;

  @Autowired
  public CategoriesItemProductsThymeleafController(CategoryService categoryService,
      ProductService productService, MessageSource messageSource,
      ControllerMethodLinkBuilderFactory linkBuilder) {
    this.categoryService = categoryService;
    this.productService = productService;
    this.messageSource = messageSource;
    this.collectionLink = linkBuilder.of(CategoriesCollectionThymeleafController.class);
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

  @GetMapping(value = "/dt", produces = Datatables.MEDIA_TYPE, name = "datatables")
  @ResponseBody
  public DatatablesData<Product> datatables(@ModelAttribute Category category, GlobalSearch search,
      Pageable pageable, @RequestParam(Datatables.PARAMETER_DRAW) Integer draw) {
    Page<Product> products = productService.findByCategoriesContains(category, search, pageable);
    long allAvailableProducts = productService.countByCategoriesContains(category);
    return new DatatablesData<Product>(products, allAvailableProducts, draw);
  }

  @GetMapping(value = "/create-form", name = "createForm")
  public ModelAndView createForm(@ModelAttribute Category category, Model model) {
    return new ModelAndView("categories/products/create");
  }

  @PostMapping(name = "create")
  public ModelAndView create(@ModelAttribute Category category,
      @RequestParam("productIds") List<Long> products, Model model) {
    for (Iterator<Long> iterator = products.iterator(); iterator.hasNext();) {
      if (iterator.next() == null) {
        iterator.remove();
      }
    }
    categoryService.setProducts(category, products);

    return new ModelAndView("redirect:" + collectionLink.to("list").toUriString());
  }

  @DeleteMapping(value = "/{product}", name = "removeFromProducts")
  @ResponseBody
  public ResponseEntity<?> removeFromProducts(@ModelAttribute Category category,
      @PathVariable("product") Long product) {
    categoryService.removeFromProducts(category, Collections.singleton(product));
    return ResponseEntity.ok().build();
  }

  /* TO BE USED WHEN WE CHANGE TO MODAL DIALOGS
  
  @PostMapping(value = "/{product}", name = "addToProducts")
  @ResponseBody
  public ResponseEntity<?> addToProducts(@ModelAttribute Category category,
      @PathVariable("product") Long product) {
    categoryService.addToProducts(category, Collections.singleton(product));
    return ResponseEntity.ok().build();
  }
  
  @PostMapping(name = "addToProducts")
  @ResponseBody
  public ResponseEntity<?> addToProducts(@ModelAttribute Category category,
      @RequestParam("products") List<Long> products) {
    for (Iterator<Long> iterator = products.iterator(); iterator.hasNext();) {
      if (iterator.next() == null) {
        iterator.remove();
      }
    }
  
    categoryService.addToProducts(category, products);
    return ResponseEntity.ok().build();
  }
  */
}

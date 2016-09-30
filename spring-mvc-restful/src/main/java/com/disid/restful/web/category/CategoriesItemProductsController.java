package com.disid.restful.web.category;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.service.api.CategoryService;
import com.disid.restful.service.api.ProductService;

import io.springlets.data.jpa.repository.support.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/categories/{category}/products")
public class CategoriesItemProductsController {

  public CategoryService categoryService;

  public ProductService productService;

  @Autowired
  public CategoriesItemProductsController(CategoryService categoryService,
      ProductService productService) {
    this.categoryService = categoryService;
    this.productService = productService;
  }

  @ModelAttribute
  public Category getCategory(@PathVariable("category") Long id) {
    return categoryService.findOne(id);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<Product> listProduct(@ModelAttribute Category category, GlobalSearch search,
      Pageable pageable) {
    Page<Product> products = productService.findAllByCategory(category, search, pageable);
    return products;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
  @ResponseBody
  public DatatablesData<Product> listProduct(@ModelAttribute Category category, GlobalSearch search,
      Pageable pageable, @RequestParam("draw") Integer draw) {
    Page<Product> products = listProduct(category, search, pageable);
    long allAvailableProducts = productService.countByCategoriesContains(category);
    return new DatatablesData<Product>(products, allAvailableProducts, draw);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Category addProduct(@ModelAttribute Category category, @RequestBody Long product) {
    return categoryService.addToProducts(category, product);
  }

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Category deleteProduct(@ModelAttribute Category category, @RequestBody Long product) {
    return categoryService.deleteFromProducts(category, product);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Category addProducts(@ModelAttribute Category category, @RequestBody Long[] products) {
    return categoryService.addToProducts(category, products);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Category deleteProducts(@ModelAttribute Category category, @RequestBody Long[] products) {
    return categoryService.deleteFromProducts(category, products);
  }

}

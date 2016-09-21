package com.disid.restful.web.product;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CategoryService;
import com.disid.restful.service.api.ProductService;

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
@RequestMapping("/products/{product}/categories")
public class ProductsItemCategoriesController {

  public ProductService productService;

  public CategoryService categoryService;

  @Autowired
  public ProductsItemCategoriesController(ProductService productService,
      CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }

  @ModelAttribute
  public Product getProduct(@PathVariable("product") Long id) {
    return productService.findOne(id);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<Category> listCategories(@ModelAttribute Product product, GlobalSearch search,
      Pageable pageable) {
    Page<Category> categories = categoryService.findAllByProduct(product, search, pageable);
    return categories;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
  @ResponseBody
  public DatatablesData<Category> listCategories(@ModelAttribute Product product,
      GlobalSearch search, Pageable pageable, @RequestParam("draw") Integer draw) {
    Page<Category> categories = listCategories(product, search, pageable);
    long allAvailableCategories = categoryService.countByProductsContains(product);
    return new DatatablesData<Category>(categories, allAvailableCategories, draw);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Product addToCategories(@ModelAttribute Product product, @RequestBody Long category) {
    return categoryService.addToProducts(product, category);
  }

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Product deleteFromCategories(@ModelAttribute Product product, @RequestBody Long category) {
    return categoryService.deleteFromProducts(product, category);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Product addCategories(@ModelAttribute Product product, @RequestBody Long[] categories) {
    //	return productService.addToCategories(product, categories);
    return categoryService.addToProducts(product, categories);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Product deleteCategories(@ModelAttribute Product product, @RequestBody Long[] categories) {
    return categoryService.deleteFromProducts(product, categories);
  }

}

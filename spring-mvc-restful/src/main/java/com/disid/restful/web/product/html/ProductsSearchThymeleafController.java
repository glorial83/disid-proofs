package com.disid.restful.web.product.html;

import com.disid.restful.model.Product;
import com.disid.restful.model.ProductByNameAndDescriptionSearchForm;
import com.disid.restful.service.api.ProductService;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesData;
import io.springlets.data.web.datatables.DatatablesPageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/products/search", produces = MediaType.TEXT_HTML_VALUE,
    name = "ProductsSearchThymeleafController")
public class ProductsSearchThymeleafController {

  public ProductService productService;

  @Autowired
  public ProductsSearchThymeleafController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(value = "/byNameAndDescription/search-form", name = "byNameAndDescriptionForm")
  public ModelAndView byNameAndDescriptionForm(
      @ModelAttribute("formBean") ProductByNameAndDescriptionSearchForm formBean) {
    return new ModelAndView("products/findByNameAndDescriptionForm");
  }

  @GetMapping(value = "/byNameAndDescription", name = "byNameAndDescription")
  public ModelAndView byNameAndDescription(
      @ModelAttribute("formBean") ProductByNameAndDescriptionSearchForm formBean) {
    return new ModelAndView("products/findByNameAndDescription");
  }

  @GetMapping(value = "/byNameAndDescription/dt", produces = Datatables.MEDIA_TYPE,
      name = "byNameAndDescriptionDt")
  @ResponseBody
  public ResponseEntity<DatatablesData<Product>> byNameAndDescriptionDt(
      @ModelAttribute("formBean") ProductByNameAndDescriptionSearchForm formBean,
      GlobalSearch search,
      DatatablesPageable pageable,
      @RequestParam(Datatables.PARAMETER_DRAW) Integer draw) {
    Page<Product> products = productService.findByNameAndDescription(formBean, search, pageable);
    long totalProductsCount = products.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalProductsCount = productService.countByNameAndDescription(formBean);
    }
    DatatablesData<Product> datatablesData =
        new DatatablesData<Product>(products, totalProductsCount, draw);
    return ResponseEntity.ok(datatablesData);
  }
}

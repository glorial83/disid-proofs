package com.disid.restful.web.product.html;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;
import com.disid.restful.web.product.Select2ProductData;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesData;
import io.springlets.data.web.datatables.DatatablesPageable;
import io.springlets.data.web.select2.Select2Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Locale;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/products", name = "ProductsCollectionThymeleafController",
    produces = MediaType.TEXT_HTML_VALUE)
public class ProductsCollectionThymeleafController {

  public ProductService productService;
  private MessageSource messageSource;

  @Autowired
  public ProductsCollectionThymeleafController(ProductService productService,
      MessageSource messageSource) {
    this.productService = productService;
    this.messageSource = messageSource;
  }

  @InitBinder("product")
  public void initOwnerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @GetMapping(value = "/create-form", name = "createForm")
  public ModelAndView createForm(Model model) {
    model.addAttribute(new Product());
    return new ModelAndView("products/create");
  }

  @PostMapping(name = "create")
  public ModelAndView create(@Valid @ModelAttribute Product product, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      return new ModelAndView("products/create");
    }
    Product newProduct = productService.save(product);
    UriComponents showURI = ProductsItemThymeleafController.showURI(newProduct);
    return new ModelAndView("redirect:" + showURI.toUriString());
  }

  @GetMapping(name = "list")
  public ModelAndView list(Model model) {
    return new ModelAndView("products/list");
  }

  public static UriComponents listURI() {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(ProductsCollectionThymeleafController.class).list(null))
        .build().encode();
  }

  @GetMapping(value = "/dt", name = "datatables", produces = Datatables.MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity<DatatablesData<Product>> datatables(GlobalSearch search,
      DatatablesPageable pageable, @RequestParam(Datatables.PARAMETER_DRAW) Integer draw) {
    Page<Product> products = productService.findAll(search, pageable);
    long totalProductsCount = products.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalProductsCount = productService.count();
    }
    DatatablesData<Product> datatablesData =
        new DatatablesData<Product>(products, totalProductsCount, draw);
    return ResponseEntity.ok(datatablesData);
  }

  /**
   * Generic select2 method which provides only the 'id' and 'text' properties needed by select2.
   */
  @GetMapping(value = "/s2", name = "select2",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Select2Data<Product>> select2(GlobalSearch search, Pageable pageable,
      Locale locale) {
    Page<Product> products = productService.findAll(search, pageable);
    String textExpression = messageSource.getMessage("expression_product", null, "#{name}", locale);
    String idExpression = "#{id}";

    Select2Data<Product> select2Data =
        new Select2Data<Product>(products, idExpression, textExpression);
    return ResponseEntity.ok(select2Data);
  }

  /**
   * Product specific select2 method which adds the 'description' property to the default
   * ones. This a sample method on how to add additional properties to the data sent
   * to a select2 component, just in case it is needed in another component.
   */
  @GetMapping(value = "/s2desc", name = "select2withDesc",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Select2ProductData> select2WithDesc(GlobalSearch search, Pageable pageable,
      Locale locale) {
    Page<Product> products = productService.findAll(search, pageable);
    String textExpression = messageSource.getMessage("expression_product", null, "#{name}", locale);
    String idExpression = "#{id}";

    Select2ProductData select2Data = new Select2ProductData(products, idExpression, textExpression);

    return ResponseEntity.ok(select2Data);
  }
}

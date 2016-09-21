package com.disid.restful.web.product;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.datatables.DatatablesPageable;
import com.disid.restful.model.Product;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.ProductService;

@Controller
@RequestMapping("/products")
public class ProductsCollectionController {

  public ProductService productService;

  @Autowired
  public ProductsCollectionController(ProductService productService) {
    this.productService = productService;
  }

  // Create Products

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity create(@Valid @RequestBody Product product, BindingResult result) {
    if (product.getId() != null) {
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    Product newProduct = productService.save(product);
    HttpHeaders responseHeaders = populateHeaders(newProduct.getId());
    return new ResponseEntity(newProduct, responseHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/create-form", method = RequestMethod.GET,
      produces = MediaType.TEXT_HTML_VALUE)
  public String createForm(Model model) {
    model.addAttribute(new Product());
    return "products/create";
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
  public String create(@Valid @ModelAttribute Product product, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "products/create";
    }
    Product newProduct = productService.save(product);
    redirectAttrs.addAttribute("id", newProduct.getId());
    return "redirect:/products/{id}";
  }

  // List Products

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String list(Model model) {
    return "products/list";
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<Product> list(GlobalSearch search, Pageable pageable) {
    Page<Product> product = productService.findAll(search, pageable);
    return product;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
  @ResponseBody
  public DatatablesData<Product> list(GlobalSearch search, DatatablesPageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<Product> product = list(search, pageable);
    long allAvailableProduct = productService.count();
    return new DatatablesData<Product>(product, allAvailableProduct, draw);
  }

  // Batch operations with Products

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity createBatch(@Valid @RequestBody Collection<Product> products,
      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    List<Product> newProducts = productService.save(products);
    return new ResponseEntity(newProducts, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity updateBatch(@Valid @RequestBody Collection<Product> products,
      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    List<Product> savedProducts = productService.save(products);
    return new ResponseEntity(savedProducts, HttpStatus.OK);
  }

  @RequestMapping(value = "/batch/{ids}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes"})
  public ResponseEntity deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    productService.delete(ids);
    return new ResponseEntity(HttpStatus.OK);
  }

  // Populates

  public HttpHeaders populateHeaders(Long id) {
    UriComponents uriComponents = UriComponentsBuilder.fromUriString("/products/{id}").build();
    URI uri = uriComponents.expand(id).encode().toUri();
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);
    return responseHeaders;
  }

}

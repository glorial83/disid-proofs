package com.disid.restful.web.product;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;

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
@RequestMapping("/products/{product}")
public class ProductsItemController {

  public ProductService productService;

  @Autowired
  public ProductsItemController(ProductService productService) {
    this.productService = productService;
  }

  @ModelAttribute
  public Product getProduct(@PathVariable("product") Long id) {
    return productService.findOne(id);
  }

  // Update Product

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity update(@ModelAttribute Product storedProduct,
      @Valid @RequestBody Product product, BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }

    if (storedProduct == null) {
      return new ResponseEntity(result, HttpStatus.NOT_FOUND);
    }

    storedProduct.setDescription(product.getDescription());
    storedProduct.setName(product.getName());
    Product savedProduct = productService.save(storedProduct);
    return new ResponseEntity(savedProduct, HttpStatus.OK);
  }

  @RequestMapping(value = "/edit-form", method = RequestMethod.GET,
      produces = MediaType.TEXT_HTML_VALUE)
  public String editForm(@ModelAttribute Product product, Model model) {
    return "products/edit";
  }

  @RequestMapping(method = RequestMethod.PUT, produces = MediaType.TEXT_HTML_VALUE)
  public String update(@Valid @ModelAttribute Product product, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "products/edit";
    }
    Product savedProduct = productService.save(product);
    redirectAttrs.addAttribute("id", savedProduct.getId());
    return "redirect:/products/{id}";
  }

  // Delete product

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
  public String delete(@ModelAttribute Product product, Model model) {
    productService.delete(product);
    return "redirect:/products";
  }

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Product> delete(@ModelAttribute Product product) {
    productService.delete(product);
    return new ResponseEntity<Product>(HttpStatus.OK);
  }

  // Show Product

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Product> show(@ModelAttribute Product product) {
    if (product == null) {
      return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Product>(product, HttpStatus.FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String show(@ModelAttribute Product product, Model model) {
    return "products/show";
  }

}

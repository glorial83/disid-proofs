package com.disid.restful.web.product.api;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/products/{product}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsItemJsonController {

  public ProductService productService;

  @Autowired
  public ProductsItemJsonController(ProductService productService) {
    this.productService = productService;
  }

  @ModelAttribute
  public Product getProduct(@PathVariable("product") Long id) {
    return productService.findOne(id);
  }

  // Update Product

  @PutMapping
  public ResponseEntity<?> update(@ModelAttribute Product storedProduct,
      @Valid @RequestBody Product product, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    if (storedProduct == null) {
      return ResponseEntity.notFound().build();
    }

    product.setId(storedProduct.getId());
    productService.save(storedProduct);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@ModelAttribute Product product) {
    productService.delete(product);
    return ResponseEntity.ok().build();
  }


  @GetMapping
  public ResponseEntity<?> show(@ModelAttribute Product product) {
    if (product == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.status(HttpStatus.FOUND).body(product);
  }

}

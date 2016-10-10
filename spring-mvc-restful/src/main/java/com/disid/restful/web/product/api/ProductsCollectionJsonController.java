package com.disid.restful.web.product.api;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Collection;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsCollectionJsonController {

  public ProductService productService;

  @Autowired
  public ProductsCollectionJsonController(ProductService productService) {
    this.productService = productService;
  }

  // Create Products

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {
    if (product.getId() != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    Product newProduct = productService.save(product);

    UriComponents showURI = ProductsItemJsonController.showURI(newProduct);
    return ResponseEntity.created(showURI.toUri()).build();
  }

  @GetMapping
  public ResponseEntity<Page<Product>> list(GlobalSearch globalSearch, Pageable pageable) {
    Page<Product> products = productService.findAll(globalSearch, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(products);
  }

  public static UriComponents listURI() {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(ProductsCollectionJsonController.class).list(null, null))
        .build().encode();
  }

  // Batch operations with Products

  @PostMapping("/batch")
  public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Product> products,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    productService.save(products);

    return ResponseEntity.created(listURI().toUri()).build();
  }

  @PutMapping("/batch")
  public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Product> products,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    productService.save(products);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/batch/{ids}")
  public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    productService.delete(ids);
    return ResponseEntity.ok().build();
  }

}

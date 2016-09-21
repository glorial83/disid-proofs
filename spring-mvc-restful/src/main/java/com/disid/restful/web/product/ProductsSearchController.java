package com.disid.restful.web.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.disid.restful.service.api.ProductService;

@Controller
@RequestMapping("/products/search")
public class ProductsSearchController {

  public ProductService productService;

  @Autowired
  public ProductsSearchController(ProductService productService) {
    this.productService = productService;
  }

}

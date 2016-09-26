package com.disid.restful.web;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;
import org.springframework.stereotype.Component;

@RooFormatter(entity = Product.class, service = ProductService.class)
@Component
public class ProductFormatter {

  @Autowired
  public ProductFormatter(ProductService productService, ConversionService conversionService) {
    this.productService = productService;
    this.conversionService = conversionService;
  }
}

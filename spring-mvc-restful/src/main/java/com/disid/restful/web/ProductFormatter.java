package com.disid.restful.web;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = Product.class, service = ProductService.class)
public class ProductFormatter {
}

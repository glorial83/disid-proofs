package com.disid.restful.config;

import org.springframework.format.FormatterRegistry;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooWebMvcConfiguration;

@RooWebMvcConfiguration
public class WebMvcConfiguration {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    //      if (!(registry instanceof FormattingConversionService)) {
    //          return;
    //      }
    //      FormattingConversionService conversionService = (FormattingConversionService) registry;
    //      
    //      // Entity Formatters
    //      conversionService.addFormatter(new CategoryFormatter(categoryService, conversionService));
    //      conversionService.addFormatter(new CustomerFormatter(customerService, conversionService));
    //      conversionService.addFormatter(new CustomerOrderFormatter(customerOrderService, conversionService));
    //      conversionService.addFormatter(new OrderDetailFormatter(orderDetailService, conversionService));
    //      conversionService.addFormatter(new ProductFormatter(productService, conversionService));
    // DO NOTHING
  }

}

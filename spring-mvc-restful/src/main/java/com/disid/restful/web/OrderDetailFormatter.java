package com.disid.restful.web;

import java.util.Locale;

import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

import com.disid.restful.model.OrderDetail;
import com.disid.restful.service.api.OrderDetailService;

@RooFormatter(entity = OrderDetail.class, service = OrderDetailService.class)
public class OrderDetailFormatter {

  public OrderDetail parse(String text, Locale locale) {
    // if (text == null || !StringUtils.hasText(text)) {
    // return null;
    // }
    // Long id = conversionService.convert(text, Long.class);
    // return orderDetailService.findOne(id);
    return null;
  }
}

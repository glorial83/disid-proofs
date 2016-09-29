package com.disid.restful.web;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;

import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = CustomerOrder.class, service = CustomerOrderService.class)
public class CustomerOrderFormatter {
}

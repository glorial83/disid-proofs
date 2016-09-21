package com.disid.restful.web;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = Customer.class, service = CustomerService.class)
public class CustomerFormatter {
}

package com.disid.restful.web.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.disid.restful.service.api.CustomerService;

@Controller
@RequestMapping("/customers/search")
public class CustomersSearchController {

  public CustomerService customerService;

  @Autowired
  public CustomersSearchController(CustomerService customerService) {
    this.customerService = customerService;
  }

}

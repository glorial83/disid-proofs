package com.disid.restful.web.api.customer;

import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/customers/search")
public class CustomersSearchRestController {

  public CustomerService customerService;

  @Autowired
  public CustomersSearchRestController(CustomerService customerService) {
    this.customerService = customerService;
  }

}

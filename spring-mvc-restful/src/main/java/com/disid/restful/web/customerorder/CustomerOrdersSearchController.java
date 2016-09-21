package com.disid.restful.web.customerorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.disid.restful.service.api.CustomerOrderService;

@Controller
@RequestMapping("/customerorders/search")
public class CustomerOrdersSearchController {

  public CustomerOrderService customerOrderService;

  @Autowired
  public CustomerOrdersSearchController(CustomerOrderService customerOrderService) {
    this.customerOrderService = customerOrderService;
  }

}

package com.disid.restful.web.customer;

import com.disid.restful.datatables.Datatables;
import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;

import io.springlets.data.jpa.repository.support.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/customers/{customer}/orders", produces = MediaType.TEXT_HTML_VALUE)
public class CustomersItemOrdersController {

  public CustomerOrderService customerOrderService;

  public CustomerService customerService;

  @Autowired
  public CustomersItemOrdersController(CustomerService customerService,
      CustomerOrderService customerOrderService) {
    this.customerService = customerService;
    this.customerOrderService = customerOrderService;
  }

  @ModelAttribute
  public Customer getCustomer(@PathVariable("customer") Long id) {
    return customerService.findOne(id);
  }

  @GetMapping(produces = Datatables.MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity<DatatablesData<CustomerOrder>> listCustomerOrder(
      @ModelAttribute Customer customer, GlobalSearch search, Pageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<CustomerOrder> customerOrderPage =
        customerOrderService.findAllByCustomer(customer, search, pageable);
    long orderDetailsWithoutSearchFilterCount = customerOrderPage.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      orderDetailsWithoutSearchFilterCount =
          customerOrderService.countByCustomerId(customer.getId());
    }
    DatatablesData<CustomerOrder> datatablesData = new DatatablesData<CustomerOrder>(
        customerOrderPage, orderDetailsWithoutSearchFilterCount, draw);
    return ResponseEntity.status(HttpStatus.FOUND).body(datatablesData);
  }

}

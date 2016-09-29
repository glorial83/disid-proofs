package com.disid.restful.web.api.customer;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers/{customer}/orders")
public class CustomersItemOrdersRestController {

  public CustomerOrderService customerOrderService;

  public CustomerService customerService;

  @Autowired
  public CustomersItemOrdersRestController(CustomerService customerService,
      CustomerOrderService customerOrderService) {
    this.customerService = customerService;
    this.customerOrderService = customerOrderService;
  }

  @ModelAttribute
  public Customer getCustomer(@PathVariable("customer") Long id) {
    return customerService.findOne(id);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<CustomerOrder>> listCustomerOrder(@ModelAttribute Customer customer,
      GlobalSearch search, Pageable pageable) {
    Page<CustomerOrder> customerOrders =
        customerOrderService.findAllByCustomer(customer, search, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(customerOrders);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addToOrders(@ModelAttribute Customer customer, @RequestBody Long order) {
    customerService.addToOrders(customer, order);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteFromOrders(@ModelAttribute Customer customer,
      @RequestBody Long order) {
    customerService.removeFromOrders(customer, order);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addToOrders(@ModelAttribute Customer customer,
      @RequestBody Long[] orders) {
    customerService.addToOrders(customer, orders);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "/batch", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteFromOrders(@ModelAttribute Customer customer,
      @RequestBody Long[] orders) {
    customerService.removeFromOrders(customer, orders);
    return ResponseEntity.ok().build();
  }

}

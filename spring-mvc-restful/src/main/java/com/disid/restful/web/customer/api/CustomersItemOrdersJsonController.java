package com.disid.restful.web.customer.api;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customers/{customer}/orders", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomersItemOrdersJsonController {

  public CustomerOrderService customerOrderService;

  public CustomerService customerService;

  @Autowired
  public CustomersItemOrdersJsonController(CustomerService customerService,
      CustomerOrderService customerOrderService) {
    this.customerService = customerService;
    this.customerOrderService = customerOrderService;
  }

  @ModelAttribute
  public Customer getCustomer(@PathVariable("customer") Long id) {
    return customerService.findOne(id);
  }

  @GetMapping
  public ResponseEntity<Page<CustomerOrder>> listCustomerOrder(@ModelAttribute Customer customer,
      Pageable pageable) {
    Page<CustomerOrder> customerOrders =
        customerOrderService.findAllByCustomer(customer, null, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(customerOrders);
  }

  @PostMapping
  public ResponseEntity<?> addToOrders(@ModelAttribute Customer customer, @RequestBody Long order) {
    customerService.addToOrders(customer, order);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> deleteFromOrders(@ModelAttribute Customer customer,
      @RequestBody Long order) {
    customerService.removeFromOrders(customer, order);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/batch")
  public ResponseEntity<?> addToOrders(@ModelAttribute Customer customer,
      @RequestBody Long[] orders) {
    customerService.addToOrders(customer, orders);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/batch")
  public ResponseEntity<?> deleteFromOrders(@ModelAttribute Customer customer,
      @RequestBody Long[] orders) {
    customerService.removeFromOrders(customer, orders);
    return ResponseEntity.ok().build();
  }

}

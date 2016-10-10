package com.disid.restful.web.customerorder.api;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/customerorders/{customerorder}",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerOrdersItemJsonController {

  public CustomerOrderService customerOrderService;
  public CustomerService customerService;

  @Autowired
  public CustomerOrdersItemJsonController(CustomerOrderService customerOrderService,
      CustomerService customerService) {
    this.customerOrderService = customerOrderService;
    this.customerService = customerService;
  }

  @ModelAttribute
  public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id) {
    CustomerOrder customerOrder = customerOrderService.findOne(id);
    if (customerOrder == null) {
      throw new NotFoundException("Order not found");
    }
    return customerOrder;
  }

  // Update Customer

  @PutMapping
  public ResponseEntity<?> update(@ModelAttribute CustomerOrder storedCustomerOrder,
      @Valid @RequestBody CustomerOrder customerOrder, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    if (storedCustomerOrder == null) {
      return ResponseEntity.notFound().build();
    }

    customerOrder.setId(storedCustomerOrder.getId());
    customerOrderService.save(customerOrder);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@ModelAttribute CustomerOrder customerOrder) {
    customerOrderService.delete(customerOrder);
    return ResponseEntity.ok().build();
  }

  // Show Customer

  @GetMapping
  public ResponseEntity<?> show(@ModelAttribute CustomerOrder customerOrder) {
    if (customerOrder == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.status(HttpStatus.FOUND).body(customerOrder);
  }

  public static UriComponents showURI(CustomerOrder customerOrder) {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(CustomerOrdersItemJsonController.class).show(customerOrder))
        .buildAndExpand(customerOrder.getId()).encode();
  }
}

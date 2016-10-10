package com.disid.restful.web.customer.api;

import com.disid.restful.model.Customer;
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
@RequestMapping(value = "/customers/{customer}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomersItemJsonController {

  public CustomerService customerService;

  @Autowired
  public CustomersItemJsonController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @ModelAttribute
  public Customer getCustomer(@PathVariable("customer") Long id) {
    Customer customer = customerService.findOne(id);
    if (customer == null) {
      throw new NotFoundException("Customer not found");
    }
    return customer;
  }

  @PutMapping
  public ResponseEntity<?> update(@ModelAttribute Customer storedCustomer,
      @Valid @RequestBody Customer customer, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    customer.setId(storedCustomer.getId());
    customer.getAddress().setId(storedCustomer.getAddress().getId());
    customerService.save(customer);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@ModelAttribute Customer customer) {
    customerService.delete(customer);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<?> show(@ModelAttribute Customer customer) {
    return ResponseEntity.status(HttpStatus.FOUND).body(customer);
  }

  public static UriComponents showURI(Customer customer) {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(CustomersItemJsonController.class).show(customer))
        .buildAndExpand(customer.getId()).encode();
  }
}

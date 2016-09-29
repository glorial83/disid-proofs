package com.disid.restful.web.api.customer;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers/{customer}")
public class CustomersItemRestController {

  public CustomerService customerService;

  @Autowired
  public CustomersItemRestController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @ModelAttribute
  public Customer getCustomer(@PathVariable("customer") Long id) {
    return customerService.findOne(id);
  }

  // Update Customer

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(@ModelAttribute Customer storedCustomer,
      @Valid @RequestBody Customer customer, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    if (storedCustomer == null) {
      return ResponseEntity.notFound().build();
    }

    customer.setId(storedCustomer.getId());

    customerService.save(customer);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@ModelAttribute Customer customer) {
    customerService.delete(customer);
    return ResponseEntity.ok().build();
  }

  // Show Customer

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> show(@ModelAttribute Customer customer) {
    if (customer == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.status(HttpStatus.FOUND).body(customer);
  }

}

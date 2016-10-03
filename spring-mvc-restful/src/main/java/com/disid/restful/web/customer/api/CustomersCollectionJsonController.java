package com.disid.restful.web.customer.api;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomersCollectionJsonController {

  public CustomerService customerService;

  @Autowired
  public CustomersCollectionJsonController(CustomerService customerService) {
    this.customerService = customerService;
  }

  // Create Customers

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Customer customer, BindingResult result) {
    if (customer.getId() != null
        || (customer.getAddress() != null && customer.getAddress().getId() != null)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    Customer newCustomer = customerService.save(customer);

    URI uri = fromMethodCall(on(CustomersItemJsonController.class).show(null))
        .buildAndExpand(newCustomer.getId()).encode().toUri();

    return ResponseEntity.created(uri).build();
  }

  // List Customers

  @GetMapping
  public ResponseEntity<Page<Customer>> list(Pageable pageable) {
    Page<Customer> customers = customerService.findAll(null, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(customers);
  }

  // Batch operations with Customers

  @PostMapping("/batch")
  public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Customer> customers,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    customerService.save(customers);

    URI uri = fromMethodCall(on(getClass()).list(null)).build().encode().toUri();
    return ResponseEntity.created(uri).build();
  }

  @PutMapping("/batch")
  public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Customer> customers,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    customerService.save(customers);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/batch/{ids}")
  public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    customerService.delete(ids);
    return ResponseEntity.ok().build();
  }

}

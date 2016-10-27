package com.disid.restful.web.customer.api;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;

import io.springlets.data.domain.GlobalSearch;

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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Collection;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/customers",
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
    if (customer.getId() != null || customer.getVersion() != null
        || (customer.getAddress() != null && (customer.getAddress().getId() != null)
            || customer.getAddress().getVersion() != null)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    Customer newCustomer = customerService.save(customer);

    UriComponents showURI = CustomersItemJsonController.showURI(newCustomer);
    return ResponseEntity.created(showURI.toUri()).build();
  }

  // List Customers

  @GetMapping
  public ResponseEntity<Page<Customer>> list(GlobalSearch globalSearch, Pageable pageable) {
    Page<Customer> customers = customerService.findAll(globalSearch, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(customers);
  }

  public static UriComponents listURI() {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(CustomersCollectionJsonController.class).list(null, null))
        .build().encode();
  }

  // Batch operations with Customers

  @PostMapping("/batch")
  public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Customer> customers,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    customerService.save(customers);

    return ResponseEntity.created(listURI().toUri()).build();
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

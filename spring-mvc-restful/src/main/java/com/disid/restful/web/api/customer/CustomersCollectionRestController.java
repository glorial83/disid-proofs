package com.disid.restful.web.api.customer;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.disid.restful.model.Customer;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomersCollectionRestController {

  public CustomerService customerService;

  @Autowired
  public CustomersCollectionRestController(CustomerService customerService) {
    this.customerService = customerService;
  }

  // Create Customers

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@Valid @RequestBody Customer customer, BindingResult result) {
    if (customer.getId() != null
        || (customer.getAddress() != null && customer.getAddress().getId() != null)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    Customer newCustomer = customerService.save(customer);

    // TODO: replace with controller uri building
    //    UriComponents uriComponents = UriComponentsBuilder.fromUriString("/customers/{id}").build();
    //    URI uri = uriComponents.expand(newCustomer.getId()).encode().toUri();

    URI uri = fromMethodCall(on(CustomersItemRestController.class).show(null))
        .buildAndExpand(newCustomer.getId()).encode().toUri();

    return ResponseEntity.created(uri).build();
  }

  // List Customers

  @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Customer>> list(GlobalSearch search, Pageable pageable) {
    Page<Customer> customers = customerService.findAll(search, pageable);
    return ResponseEntity.ok(customers);
  }

  // Batch operations with Customers

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Customer> customers,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    customerService.save(customers);

    URI uri = fromMethodCall(on(getClass()).list(null, null)).build().encode().toUri();

    return ResponseEntity.created(uri).build();
  }

  @RequestMapping(value = "/batch", method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Customer> customers,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    customerService.save(customers);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "/batch/{ids}", method = RequestMethod.DELETE,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    customerService.delete(ids);
    return ResponseEntity.ok().build();
  }

}

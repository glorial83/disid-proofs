package com.disid.restful.web.customerorder.api;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;
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
@RequestMapping(value = "/customerorders", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerOrdersCollectionJsonController {

  public CustomerOrderService customerOrderService;
  public CustomerService customerService;

  @Autowired
  public CustomerOrdersCollectionJsonController(CustomerOrderService customerOrderService,
      CustomerService customerService) {
    this.customerOrderService = customerOrderService;
    this.customerService = customerService;
  }

  // Create Customers

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody CustomerOrder customerOrder,
      BindingResult result) {
    if (customerOrder.getId() != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    CustomerOrder newCustomerOrder = customerOrderService.save(customerOrder);

    URI uri = fromMethodCall(on(CustomerOrdersItemJsonController.class).show(null))
        .buildAndExpand(newCustomerOrder.getId()).encode().toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<CustomerOrder>> list(Pageable pageable) {
    Page<CustomerOrder> customerOrders = customerOrderService.findAll(null, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(customerOrders);
  }

  // Batch operations with Customers

  @PostMapping("/batch")
  public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<CustomerOrder> customerOrders,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    customerOrderService.save(customerOrders);

    URI uri = fromMethodCall(on(getClass()).list(null)).build().encode().toUri();
    return ResponseEntity.created(uri).build();
  }

  @PutMapping("/batch")
  public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<CustomerOrder> customerOrders,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    customerOrderService.save(customerOrders);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/batch/{ids}")
  public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    customerOrderService.delete(ids);
    return ResponseEntity.ok().build();
  }

}

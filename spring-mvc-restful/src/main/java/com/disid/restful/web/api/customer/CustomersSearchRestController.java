package com.disid.restful.web.api.customer;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerSearchForm;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/customers/search", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomersSearchRestController {

  public CustomerService customerService;

  @Autowired
  public CustomersSearchRestController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping(value = "/byFirstNameLastName")
  public ResponseEntity<Page<Customer>> findByFirstNameLastName(CustomerSearchForm formBean,
      Pageable pageable) {
    Page<Customer> customers = customerService.findByFirstNameLastName(formBean, null, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(customers);
  }

}

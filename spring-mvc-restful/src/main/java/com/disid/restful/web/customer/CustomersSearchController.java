package com.disid.restful.web.customer;

import com.disid.restful.datatables.Datatables;
import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.datatables.DatatablesPageable;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerSearchForm;
import com.disid.restful.service.api.CustomerService;

import io.springlets.data.jpa.repository.support.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/customers/search", produces = MediaType.TEXT_HTML_VALUE)
public class CustomersSearchController {

  public CustomerService customerService;

  @Autowired
  public CustomersSearchController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping(value = "/byFirstNameLastName")
  public String findByFirstNameLastName(Model model) {
    return "customers/findByFirstNameLastName";
  }

  @GetMapping(value = "/byFirstNameLastName", produces = Datatables.MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity<DatatablesData<Customer>> findByFirstNameLastName(
      CustomerSearchForm formBean, GlobalSearch search, DatatablesPageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<Customer> customers = customerService.findByFirstNameLastName(formBean, search, pageable);
    long allAvailableCustomers = customerService.countByFirstNameLastName(formBean);
    DatatablesData<Customer> datatablesData =
        new DatatablesData<Customer>(customers, allAvailableCustomers, draw);
    return ResponseEntity.status(HttpStatus.FOUND).body(datatablesData);
  }
}

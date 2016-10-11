package com.disid.restful.web.customer.html;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerSearchForm;
import com.disid.restful.service.api.CustomerService;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesData;
import io.springlets.data.web.datatables.DatatablesPageable;

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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/customers/search", produces = MediaType.TEXT_HTML_VALUE)
public class CustomersSearchThymeleafController {

  public CustomerService customerService;

  @Autowired
  public CustomersSearchThymeleafController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/byFirstNameLastName")
  public ModelAndView findByFirstNameLastName(Model model) {
    return new ModelAndView("customers/findByFirstNameLastName");
  }

  @GetMapping(value = "/byFirstNameLastName", produces = Datatables.MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity<DatatablesData<Customer>> findByFirstNameLastName(
      CustomerSearchForm formBean, GlobalSearch search, DatatablesPageable pageable,
      @RequestParam(Datatables.PARAMETER_DRAW) Integer draw) {
    Page<Customer> customers = customerService.findByFirstNameLastName(formBean, search, pageable);
    long allAvailableCustomers = customerService.countByFirstNameLastName(formBean);
    DatatablesData<Customer> datatablesData =
        new DatatablesData<Customer>(customers, allAvailableCustomers, draw);
    return ResponseEntity.status(HttpStatus.FOUND).body(datatablesData);
  }
}

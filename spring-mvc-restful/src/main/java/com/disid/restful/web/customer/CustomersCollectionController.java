package com.disid.restful.web.customer;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.datatables.DatatablesPageable;
import com.disid.restful.model.Customer;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers")
public class CustomersCollectionController {

  public CustomerService customerService;

  @Autowired
  public CustomersCollectionController(CustomerService customerService) {
    this.customerService = customerService;
  }

  // Create Customers

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"unchecked", "rawtypes"})
  public ResponseEntity create(@Valid @RequestBody Customer customer, BindingResult result) {
    if (customer.getId() != null) {
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    Customer newCustomer = customerService.save(customer);
    HttpHeaders responseHeaders = populateHeaders(newCustomer.getId());
    return new ResponseEntity(newCustomer, responseHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/create-form", method = RequestMethod.GET,
      produces = MediaType.TEXT_HTML_VALUE)
  public String createForm(Model model) {
    model.addAttribute(new Customer());
    return "customers/create";
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
  public String create(@Valid @ModelAttribute Customer customer, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "customers/create";
    }
    Customer newCustomer = customerService.save(customer);
    redirectAttrs.addAttribute("id", newCustomer.getId());
    return "redirect:/customers/{id}";
  }

  // List Customers

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<Customer> list(GlobalSearch search, Pageable pageable) {
    Page<Customer> customer = customerService.findAll(search, pageable);
    return customer;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String list(Model model) {
    return "customers/list";
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
  @ResponseBody
  public DatatablesData<Customer> list(GlobalSearch search, DatatablesPageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<Customer> customer = customerService.findAll(search, pageable);
    long allAvailableCustomer = customerService.count();
    return new DatatablesData<Customer>(customer, allAvailableCustomer, draw);
  }

  // Batch operations with Customers

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity createBatch(@Valid @RequestBody Collection<Customer> customers,
      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    List<Customer> newCustomers = customerService.save(customers);
    return new ResponseEntity(newCustomers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity updateBatch(@Valid @RequestBody Collection<Customer> customers,
      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    List<Customer> savedCustomers = customerService.save(customers);
    return new ResponseEntity(savedCustomers, HttpStatus.OK);
  }

  @RequestMapping(value = "/batch/{ids}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes"})
  public ResponseEntity deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    customerService.delete(ids);
    return new ResponseEntity(HttpStatus.OK);
  }

  // Populates

  private HttpHeaders populateHeaders(Long id) {
    UriComponents uriComponents = UriComponentsBuilder.fromUriString("/customers/{id}").build();
    URI uri = uriComponents.expand(id).encode().toUri();
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);
    return responseHeaders;
  }
}

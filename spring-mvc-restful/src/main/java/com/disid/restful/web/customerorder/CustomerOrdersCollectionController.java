package com.disid.restful.web.customerorder;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.datatables.DatatablesPageable;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;
import com.disid.restful.web.CustomerFormatter;

import io.springlets.data.jpa.repository.support.GlobalSearch;

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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/customerorders")
public class CustomerOrdersCollectionController {

  public CustomerOrderService customerOrderService;
  public CustomerService customerService;

  @Autowired
  public CustomerOrdersCollectionController(CustomerOrderService customerOrderService,
      CustomerService customerService) {
    this.customerOrderService = customerOrderService;
    this.customerService = customerService;
  }

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder
        .addCustomFormatter(new CustomerFormatter(customerService, binder.getConversionService()));
  }

  // Create Customers

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"unchecked", "rawtypes"})
  public ResponseEntity create(@Valid @RequestBody CustomerOrder customerOrder,
      BindingResult result) {
    if (customerOrder.getId() != null) {
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    CustomerOrder newCustomerOrder = customerOrderService.save(customerOrder);
    HttpHeaders responseHeaders = populateHeaders(newCustomerOrder.getId());
    return new ResponseEntity(newCustomerOrder, responseHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/create-form", method = RequestMethod.GET,
      produces = MediaType.TEXT_HTML_VALUE)
  public String createForm(Model model) {
    model.addAttribute(new Customer());
    return "customerorders/create";
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
  public String create(@Valid @ModelAttribute CustomerOrder customerOrder, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "customerorders/create";
    }
    CustomerOrder newCustomerOrder = customerOrderService.save(customerOrder);
    redirectAttrs.addAttribute("id", newCustomerOrder.getId());
    return "redirect:/customerorders/{id}";
  }

  // List Customers

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<CustomerOrder> list(GlobalSearch search, Pageable pageable) {
    Page<CustomerOrder> customerOrder = customerOrderService.findAll(search, pageable);
    return customerOrder;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String list(Model model) {
    return "customerorders/list";
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
  @ResponseBody
  public DatatablesData<CustomerOrder> list(GlobalSearch search, DatatablesPageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<CustomerOrder> customerOrders = customerOrderService.findAll(search, pageable);
    long allAvailableCustomerOrders = customerOrderService.count();
    return new DatatablesData<CustomerOrder>(customerOrders, allAvailableCustomerOrders, draw);
  }

  // Batch operations with Customers

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity createBatch(@Valid @RequestBody Collection<CustomerOrder> customerOrders,
      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    List<CustomerOrder> newCustomerOrders = customerOrderService.save(customerOrders);
    return new ResponseEntity(newCustomerOrders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity updateBatch(@Valid @RequestBody Collection<CustomerOrder> customerOrders,
      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    List<CustomerOrder> savedCustomerOrders = customerOrderService.save(customerOrders);
    return new ResponseEntity(savedCustomerOrders, HttpStatus.OK);
  }

  @RequestMapping(value = "/batch/{ids}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes"})
  public ResponseEntity deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    customerOrderService.delete(ids);
    return new ResponseEntity(HttpStatus.OK);
  }

  // Populates

  private HttpHeaders populateHeaders(Long id) {
    UriComponents uriComponents =
        UriComponentsBuilder.fromUriString("/customerorders/{id}").build();
    URI uri = uriComponents.expand(id).encode().toUri();
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);
    return responseHeaders;
  }
}

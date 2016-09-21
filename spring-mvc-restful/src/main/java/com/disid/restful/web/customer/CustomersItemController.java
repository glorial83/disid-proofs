package com.disid.restful.web.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;

@Controller
@RequestMapping("/customers/{customer}")
public class CustomersItemController {

  public CustomerService customerService;

  @Autowired
  public CustomersItemController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @ModelAttribute
  public Customer getCustomer(@PathVariable("customer") Long id) {
    return customerService.findOne(id);
  }

  // Update Customer

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> update(@ModelAttribute Customer storedCustomer,
      @Valid @RequestBody Customer customer, BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity<BindingResult>(result, HttpStatus.CONFLICT);
    }

    if (storedCustomer == null) {
      return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
    }

    storedCustomer.setFirstName(customer.getFirstName());
    storedCustomer.setLastName(customer.getLastName());
    Customer savedCustomer = customerService.save(storedCustomer);
    return new ResponseEntity<Customer>(savedCustomer, HttpStatus.OK);
  }

  @RequestMapping(value = "/edit-form", method = RequestMethod.GET,
      produces = MediaType.TEXT_HTML_VALUE)
  public String editForm(@ModelAttribute Customer customer, Model model) {
    return "customers/edit";
  }

  @RequestMapping(method = RequestMethod.PUT, produces = MediaType.TEXT_HTML_VALUE)
  public String update(@Valid @ModelAttribute Customer customer, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "customers/edit";
    }
    Customer savedCustomer = customerService.save(customer);
    redirectAttrs.addAttribute("id", savedCustomer.getId());
    return "redirect:/customers/{id}";
  }

  // Delete Customer

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
  public String delete(@ModelAttribute Customer customer, Model model) {
    customerService.delete(customer);
    return "redirect:/customers";
  }

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> delete(@ModelAttribute Customer customer) {
    customerService.delete(customer);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // Show Customer

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Customer> show(@ModelAttribute Customer customer) {
    if (customer == null) {
      return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String show(@ModelAttribute Customer customer, Model model) {
    return "customers/show";
  }
}

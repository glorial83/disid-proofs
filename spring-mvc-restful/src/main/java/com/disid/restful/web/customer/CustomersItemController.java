package com.disid.restful.web.customer;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers/{customer}")
public class CustomersItemController {

  public CustomerService customerService;

  @Autowired
  public CustomersItemController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @InitBinder("customer")
  public void initCustomerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
    dataBinder.setDisallowedFields("address.id");
  }

  @ModelAttribute
  public Customer getCustomer(@PathVariable("customer") Long id) {
    return customerService.findOne(id);
  }

  // Update Customer

  @RequestMapping(value = "/edit-form", method = RequestMethod.GET,
      produces = MediaType.TEXT_HTML_VALUE)
  public String editForm(@ModelAttribute Customer customer, Model model) {

    // TODO: what happens if the customer to edit does not exist?

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

  // Show Customer

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String show(@ModelAttribute Customer customer, Model model) {
    return "customers/show";
  }
}

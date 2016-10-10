package com.disid.restful.web.customer.html;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/customers/{customer}", produces = MediaType.TEXT_HTML_VALUE)
public class CustomersItemThymeleafController {

  public CustomerService customerService;

  public MessageSource messageSource;

  @Autowired
  public CustomersItemThymeleafController(CustomerService customerService,
      MessageSource messageSource) {
    this.customerService = customerService;
    this.messageSource = messageSource;
  }

  @InitBinder("customer")
  public void initCustomerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
    dataBinder.setDisallowedFields("address.id");
  }

  @ModelAttribute
  public Customer getCustomer(@PathVariable("customer") Long id, Locale locale) {
    Customer customer = customerService.findOne(id);
    if (customer == null) {
      String message = messageSource.getMessage("error_customerNotFound", null, locale);
      throw new NotFoundException(message);
    }
    return customer;
  }

  @GetMapping("/edit-form")
  public String editForm(@ModelAttribute Customer customer, Model model) {
    return "customers/edit";
  }

  @PutMapping
  public String update(@Valid @ModelAttribute Customer customer, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "customers/edit";
    }

    Customer savedCustomer = customerService.save(customer);

    String uri = fromMethodCall(on(CustomersItemThymeleafController.class).show(null, null))
        .buildAndExpand(savedCustomer.getId()).encode().toUriString();
    return "redirect:" + uri;
  }

  @DeleteMapping
  public String delete(@ModelAttribute Customer customer, Model model) {
    customerService.delete(customer);
    String uri = fromMethodCall(on(CustomersCollectionThymeleafController.class).list(null)).build()
        .encode().toUriString();
    return "redirect:" + uri;
  }

  @GetMapping
  public String show(@ModelAttribute Customer customer, Model model) {
    return "customers/show";
  }
}

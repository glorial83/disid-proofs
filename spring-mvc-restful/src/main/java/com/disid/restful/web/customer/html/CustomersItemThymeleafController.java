package com.disid.restful.web.customer.html;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;

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
  public ModelAndView editForm(@ModelAttribute Customer customer, Model model) {
    return new ModelAndView("customers/edit");
  }

  @PutMapping
  public ModelAndView update(@Valid @ModelAttribute Customer customer, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return new ModelAndView("customers/edit");
    }

    Customer savedCustomer = customerService.save(customer);

    UriComponents showURI = CustomersItemThymeleafController.showURI(savedCustomer);
    return new ModelAndView("redirect:" + showURI.toUriString());
  }

  @DeleteMapping
  public ModelAndView delete(@ModelAttribute Customer customer, Model model) {
    customerService.delete(customer);
    UriComponents listURI = CustomersCollectionThymeleafController.listURI();
    return new ModelAndView("redirect:" + listURI.toUriString());
  }

  @GetMapping
  public ModelAndView show(@ModelAttribute Customer customer, Model model) {
    return new ModelAndView("customers/show");
  }

  public static UriComponents showURI(Customer customer) {
    return MvcUriComponentsBuilder
        .fromMethodCall(
            MvcUriComponentsBuilder.on(CustomersItemThymeleafController.class).show(customer, null))
        .buildAndExpand(customer.getId()).encode();
  }
}

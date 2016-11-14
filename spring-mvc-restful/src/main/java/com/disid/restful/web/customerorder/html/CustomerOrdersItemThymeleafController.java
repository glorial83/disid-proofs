package com.disid.restful.web.customerorder.html;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Locale;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/customerorders/{customerorder}", produces = MediaType.TEXT_HTML_VALUE,
    name = "CustomerOrdersItemThymeleafController")
public class CustomerOrdersItemThymeleafController {

  public CustomerOrderService customerOrderService;
  private MessageSource messageSource;

  @Autowired
  public CustomerOrdersItemThymeleafController(CustomerOrderService customerOrderService,
      MessageSource messageSource) {
    this.customerOrderService = customerOrderService;
    this.messageSource = messageSource;
  }

  @InitBinder("customerOrder")
  public void initCustomerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @ModelAttribute
  public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id, Locale locale) {
    CustomerOrder customerOrder = customerOrderService.findOne(id);
    if (customerOrder == null) {
      String message = messageSource.getMessage("error_customerOrderNotFound", null, locale);
      throw new NotFoundException(message);
    }
    return customerOrder;
  }

  @GetMapping(value = "/edit-form", name = "editForm")
  public ModelAndView editForm(@ModelAttribute CustomerOrder customerOrder, Model model) {
    return new ModelAndView("customerorders/edit");
  }

  @PutMapping(name = "update")
  public ModelAndView update(@Valid @ModelAttribute CustomerOrder customerOrder,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      return new ModelAndView("customerorders/edit");
    }
    CustomerOrder savedCustomerOrder = customerOrderService.save(customerOrder);

    UriComponents showURI = CustomerOrdersItemThymeleafController.showURI(savedCustomerOrder);
    return new ModelAndView("redirect:" + showURI.toUriString());
  }

  @DeleteMapping(name = "delete")
  @ResponseBody
  public ResponseEntity<?> delete(@ModelAttribute CustomerOrder customerOrder) {
    customerOrderService.delete(customerOrder);
    return ResponseEntity.ok().build();
  }

  @GetMapping(name = "show")
  public ModelAndView show(@ModelAttribute CustomerOrder customerOrder, Model model) {
    return new ModelAndView("customerorders/show");
  }

  public static UriComponents showURI(CustomerOrder customerOrder) {
    return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder
        .on(CustomerOrdersItemThymeleafController.class).show(customerOrder, null))
        .buildAndExpand(customerOrder.getId()).encode();
  }
}

package com.disid.restful.web.customerorder.html;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/customerorders/{customerorder}", produces = MediaType.TEXT_HTML_VALUE)
public class CustomerOrdersItemThymeleafController {

  public CustomerOrderService customerOrderService;
  public CustomerService customerService;

  @Autowired
  public CustomerOrdersItemThymeleafController(CustomerOrderService customerOrderService,
      CustomerService customerService) {
    this.customerOrderService = customerOrderService;
    this.customerService = customerService;
  }

  @InitBinder("customerOrder")
  public void initCustomerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @ModelAttribute
  public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id) {
    return customerOrderService.findOne(id);
  }

  @GetMapping("/edit-form")
  public String editForm(@ModelAttribute CustomerOrder customerOrder, Model model) {
    return "customerorders/edit";
  }

  @PutMapping
  public String update(@Valid @ModelAttribute CustomerOrder customerOrder, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "customerorders/edit";
    }
    CustomerOrder savedCustomerOrder = customerOrderService.save(customerOrder);
    redirectAttrs.addAttribute("id", savedCustomerOrder.getId());
    return "redirect:/customerorders/{id}";
  }

  @DeleteMapping
  public String delete(@ModelAttribute CustomerOrder customerOrder, Model model) {
    customerOrderService.delete(customerOrder);
    return "redirect:/customerorders";
  }

  @GetMapping
  public String show(@ModelAttribute CustomerOrder customerOrder, Model model) {
    return "customerorders/show";
  }
}

package com.disid.restful.web.customer.html;

import com.disid.restful.datatables.Datatables;
import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.datatables.DatatablesPageable;
import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

@Controller
// TODO: no tienen consumes??
@RequestMapping(value = "/customers", produces = MediaType.TEXT_HTML_VALUE)
public class CustomersCollectionThymeleafController {

  public CustomerService customerService;

  @Autowired
  public CustomersCollectionThymeleafController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @InitBinder("customer")
  public void initOwnerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
    dataBinder.setDisallowedFields("address.id");
  }

  // Create Customers
  @GetMapping("/create-form")
  public ModelAndView createForm(Model model) {
    model.addAttribute(new Customer());
    return new ModelAndView("customers/create");
  }

  @PostMapping
  public ModelAndView create(@Valid @ModelAttribute Customer customer, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return new ModelAndView("customers/create");
    }
    Customer newCustomer = customerService.save(customer);

    UriComponents showURI = CustomersItemThymeleafController.showURI(newCustomer);
    return new ModelAndView("redirect:" + showURI.toUriString());
  }

  // List Customers
  @GetMapping
  public ModelAndView list(Model model) {
    return new ModelAndView("customers/list");
  }

  public static UriComponents listURI() {
    return MvcUriComponentsBuilder.fromMethodCall(
        MvcUriComponentsBuilder.on(CustomersCollectionThymeleafController.class).list(null))
        .build().encode();
  }

  @GetMapping(produces = Datatables.MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity<DatatablesData<Customer>> list(GlobalSearch search,
      DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
    Page<Customer> customers = customerService.findAll(search, pageable);
    long totalCustomersCount = customers.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalCustomersCount = customerService.count();
    }
    DatatablesData<Customer> datatablesData =
        new DatatablesData<Customer>(customers, totalCustomersCount, draw);
    return ResponseEntity.ok(datatablesData);
  }

}

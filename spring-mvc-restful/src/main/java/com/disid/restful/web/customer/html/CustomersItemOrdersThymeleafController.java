package com.disid.restful.web.customer.html;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesData;
import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
@RequestMapping(value = "/customers/{customer}/orders", produces = MediaType.TEXT_HTML_VALUE)
public class CustomersItemOrdersThymeleafController {

  public CustomerOrderService customerOrderService;

  public CustomerService customerService;

  public MessageSource messageSource;

  @Autowired
  public CustomersItemOrdersThymeleafController(CustomerService customerService,
      CustomerOrderService customerOrderService, MessageSource messageSource) {
    this.customerService = customerService;
    this.customerOrderService = customerOrderService;
    this.messageSource = messageSource;
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

  @GetMapping(produces = Datatables.MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity<DatatablesData<CustomerOrder>> listCustomerOrder(
      @ModelAttribute Customer customer, GlobalSearch search, Pageable pageable,
      @RequestParam(Datatables.PARAMETER_DRAW) Integer draw) {
    Page<CustomerOrder> customerOrders =
        customerOrderService.findByCustomer(customer, search, pageable);
    long totalCustomerOrderCount = customerOrders.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalCustomerOrderCount =
          customerOrderService.countByCustomer(customer);
    }
    DatatablesData<CustomerOrder> datatablesData = new DatatablesData<CustomerOrder>(
        customerOrders, totalCustomerOrderCount, draw);
    return ResponseEntity.status(HttpStatus.FOUND).body(datatablesData);
  }

}

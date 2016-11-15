package com.disid.restful.web.customerorder.html;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.OrderDetailService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;

import java.util.Collections;
import java.util.Locale;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/customerorders/{customerorder}/details/{orderdetail}",
    produces = MediaType.TEXT_HTML_VALUE, name = "CustomerOrdersItemDetailsItemThymeleafController")
public class CustomerOrdersItemDetailsItemThymeleafController {

  public CustomerOrderService customerOrderService;
  public OrderDetailService orderDetailService;
  private MessageSource messageSource;

  @Autowired
  public CustomerOrdersItemDetailsItemThymeleafController(CustomerOrderService customerOrderService,
      OrderDetailService orderDetailService, MessageSource messageSource) {
    this.customerOrderService = customerOrderService;
    this.orderDetailService = orderDetailService;
    this.messageSource = messageSource;
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

  @ModelAttribute
  public OrderDetail getOrderDetail(@ModelAttribute CustomerOrder customerOrder,
      @PathVariable("orderdetail") Integer id, Locale locale) {
    OrderDetailPK detailPk = new OrderDetailPK(customerOrder.getId(), id);
    OrderDetail detail = orderDetailService.findOne(detailPk);
    if (detail == null) {
      String message = messageSource.getMessage("error_orderDetailNotFound", null, locale);
      throw new NotFoundException(message);
    }
    return detail;
  }

  @GetMapping(name = "show")
  public ModelAndView show(@ModelAttribute CustomerOrder customerOrder,
      @ModelAttribute OrderDetail detail, Model model) {
    return new ModelAndView("customerorders/orderdetails/show");
  }

  @GetMapping(value = "/edit-form", name = "editForm")
  public ModelAndView editForm(@ModelAttribute CustomerOrder customerOrder,
      @ModelAttribute OrderDetail detail, Model model) {
    return new ModelAndView("customerorders/orderdetails/edit");
  }

  @PutMapping(name = "update")
  public ModelAndView update(@ModelAttribute CustomerOrder customerOrder,
      @Valid @ModelAttribute OrderDetail orderDetail,
      BindingResult result, Model model) {

    if (result.hasErrors()) {
      return new ModelAndView("customerorders/orderdetails/edit");
    }

    orderDetailService.save(orderDetail);

    UriComponents listURI = CustomerOrdersCollectionThymeleafController.listURI();
    return new ModelAndView("redirect:" + listURI.toUriString());
  }

  @DeleteMapping(name = "delete")
  @ResponseBody
  public ResponseEntity<?> delete(@ModelAttribute CustomerOrder customerOrder,
      @ModelAttribute OrderDetail detail) {
    customerOrderService.removeFromDetails(customerOrder,
        Collections.singleton(detail.getId().getId()));
    return ResponseEntity.ok().build();
  }

}

package com.disid.restful.web.customerorder.api;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.OrderDetailService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Locale;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/customerorders/{customerorder}/details/{orderdetail}",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerOrdersItemDetailsItemJsonController {

  public CustomerOrderService customerOrderService;
  public OrderDetailService orderDetailService;

  @Autowired
  public CustomerOrdersItemDetailsItemJsonController(CustomerOrderService customerOrderService,
      OrderDetailService orderDetailService) {
    this.customerOrderService = customerOrderService;
    this.orderDetailService = orderDetailService;
  }

  @ModelAttribute
  public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id) {
    CustomerOrder customerOrder = customerOrderService.findOne(id);
    if (customerOrder == null) {
      throw new NotFoundException("Order not found");
    }
    return customerOrder;
  }

  @ModelAttribute
  public OrderDetail getOrderDetail(@ModelAttribute CustomerOrder customerOrder,
      @PathVariable("orderdetail") Integer id, Locale locale) {
    OrderDetailPK detailPk = new OrderDetailPK(customerOrder.getId(), id);
    OrderDetail detail = orderDetailService.findOne(detailPk);
    if (detail == null) {
      throw new NotFoundException("Order not found");
    }
    return detail;
  }

  @GetMapping
  public ResponseEntity<?> show(@ModelAttribute CustomerOrder customerOrder,
      @ModelAttribute OrderDetail detail, Model model) {
    if (detail == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.status(HttpStatus.FOUND).body(detail);
  }

  @PutMapping
  public ResponseEntity<?> update(@ModelAttribute CustomerOrder customerOrder,
      @ModelAttribute OrderDetail storedOrderDetail, @Valid @RequestBody OrderDetail orderDetail,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    orderDetail.setId(storedOrderDetail.getId());
    orderDetailService.save(orderDetail);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@ModelAttribute CustomerOrder customerOrder,
      @ModelAttribute OrderDetail detail) {
    customerOrderService.removeFromDetails(customerOrder,
        Collections.singleton(detail.getId().getId()));
    return ResponseEntity.ok().build();
  }

}

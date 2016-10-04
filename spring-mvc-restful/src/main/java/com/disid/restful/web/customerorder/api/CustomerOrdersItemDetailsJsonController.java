package com.disid.restful.web.customerorder.api;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.OrderDetailService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/customerorders/{customerorder}/details",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerOrdersItemDetailsJsonController {

  public CustomerOrderService customerOrderService;
  public OrderDetailService orderDetailService;

  @Autowired
  public CustomerOrdersItemDetailsJsonController(CustomerOrderService customerOrderService,
      OrderDetailService orderDetailService) {
    this.customerOrderService = customerOrderService;
    this.orderDetailService = orderDetailService;
  }

  @ModelAttribute
  public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id) {
    return customerOrderService.findOne(id);
  }

  @GetMapping
  public ResponseEntity<Page<OrderDetail>> listOrderDetails(
      @ModelAttribute("customerorder") CustomerOrder customerOrder, GlobalSearch globalSearch,
      Pageable pageable) {
    Page<OrderDetail> orderDetails =
        orderDetailService.findByCustomerOrder(customerOrder, globalSearch, pageable);
    return ResponseEntity.status(HttpStatus.FOUND).body(orderDetails);
  }

  @PostMapping
  public ResponseEntity<?> addToDetails(@ModelAttribute CustomerOrder customerOrder,
      @Valid @RequestBody OrderDetail detail) {
    customerOrderService.addToDetails(customerOrder, detail);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> deleteFromDetails(@ModelAttribute CustomerOrder customerOrder,
      @Valid @RequestBody OrderDetail detail) {
    customerOrderService.removeFromDetails(customerOrder, detail);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/batch")
  public ResponseEntity<?> addToDetails(@ModelAttribute CustomerOrder customerOrder,
      @Valid @RequestBody OrderDetail[] details) {
    customerOrderService.addToDetails(customerOrder, details);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/batch")
  public ResponseEntity<?> deleteFromDetails(@ModelAttribute CustomerOrder customerOrder,
      @Valid @RequestBody OrderDetail[] details) {
    customerOrderService.removeFromDetails(customerOrder, details);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{orderDetail}")
  public ResponseEntity<?> show(@ModelAttribute CustomerOrder customerOrder,
      @PathVariable("orderDetail") Integer orderDetailId) {

    OrderDetail orderDetail = null;
    if (orderDetailId != null) {
      orderDetail =
          orderDetailService.findOne(new OrderDetailPK(customerOrder.getId(), orderDetailId));
    }
    if (orderDetail == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.status(HttpStatus.FOUND).body(orderDetail);
  }

}

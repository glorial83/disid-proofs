package com.disid.restful.web.customerorder;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.service.api.OrderDetailService;

@Controller
@RequestMapping("/customerorders/{customerOrder}/details/{orderDetail}")
public class CustomerOrdersItemDetailsItemController {

  public OrderDetailService orderDetailService;

  @Autowired
  public CustomerOrdersItemDetailsItemController(OrderDetailService orderDetailService) {
    this.orderDetailService = orderDetailService;
  }

  @ModelAttribute
  public OrderDetail getOrderDetail(@PathVariable("customerOrder") Long customerOrderId,
      @PathVariable("orderDetail") Integer orderDetailId) {
    OrderDetail orderDetail =
        orderDetailService.findOne(new OrderDetailPK(customerOrderId, orderDetailId));
    return orderDetail;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<OrderDetail> show(@ModelAttribute OrderDetail orderDetail) {
    if (orderDetail == null) {
      return new ResponseEntity<OrderDetail>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<OrderDetail>(orderDetail, HttpStatus.FOUND);
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> update(@ModelAttribute OrderDetail storedOrderDetail,
      @Valid @RequestBody OrderDetail orderDetail, BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity<BindingResult>(result, HttpStatus.CONFLICT);
    }

    if (storedOrderDetail == null) {
      return new ResponseEntity<OrderDetail>(HttpStatus.NOT_FOUND);
    }

    storedOrderDetail.setProduct(orderDetail.getProduct());
    storedOrderDetail.setQuantity(orderDetail.getQuantity());
    OrderDetail savedOrderDetail = orderDetailService.save(storedOrderDetail);
    return new ResponseEntity<OrderDetail>(savedOrderDetail, HttpStatus.OK);
  }

}

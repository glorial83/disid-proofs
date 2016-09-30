package com.disid.restful.web.customerorder;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CustomerOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/customerorders/{customerorder}/details")
public class CustomerOrdersItemDetailsController {

  public CustomerOrderService customerOrderService;

  @Autowired
  public CustomerOrdersItemDetailsController(CustomerOrderService customerOrderService) {
    this.customerOrderService = customerOrderService;
  }

  @ModelAttribute("customerorder")
  public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id) {
    return customerOrderService.findOne(id);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<OrderDetail> listOrderDetails(
      @PathVariable("customerorder") CustomerOrder customerOrder, GlobalSearch search,
      Pageable pageable) {
    Page<OrderDetail> orderDetails =
        customerOrderService.findDetailsByCustomerOrder(customerOrder, search, pageable);
    return orderDetails;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
  @ResponseBody
  public DatatablesData<OrderDetail> listOrderDetails(
      @PathVariable("customerorder") CustomerOrder customerOrder, GlobalSearch search,
      Pageable pageable, @RequestParam("draw") Integer draw) {
    Page<OrderDetail> orderDetails =
        customerOrderService.findDetailsByCustomerOrder(customerOrder, search, pageable);
    long allAvailableOrderDetails = customerOrderService.countDetailsByCustomerOrder(customerOrder);
    return new DatatablesData<OrderDetail>(orderDetails, allAvailableOrderDetails, draw);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public CustomerOrder addToDetails(@ModelAttribute CustomerOrder customerOrder,
      @Valid @RequestBody OrderDetail detail) {
    return customerOrderService.addToDetails(customerOrder, detail);
  }

  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public CustomerOrder deleteFromDetails(@ModelAttribute CustomerOrder customerOrder,
      @Valid @RequestBody OrderDetail detail) {
    return customerOrderService.removeFromDetails(customerOrder, detail);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public CustomerOrder addToDetails(@ModelAttribute CustomerOrder customerOrder,
      @Valid @RequestBody OrderDetail[] details) {
    return customerOrderService.addToDetails(customerOrder, details);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public CustomerOrder deleteFromDetails(@ModelAttribute CustomerOrder customerOrder,
      @Valid @RequestBody OrderDetail[] details) {
    return customerOrderService.removeFromDetails(customerOrder, details);
  }

  @RequestMapping(value = "/{orderDetail}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<OrderDetail> show(@ModelAttribute CustomerOrder customerOrder,
      @PathVariable("orderDetail") Integer orderDetailId) {

    OrderDetail orderDetail = null;
    if (orderDetailId != null) {
      orderDetail = customerOrderService
          .findOneOrderDetail(new OrderDetailPK(customerOrder.getId(), orderDetailId));
    }
    if (orderDetail == null) {
      return new ResponseEntity<OrderDetail>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<OrderDetail>(orderDetail, HttpStatus.FOUND);
  }

}

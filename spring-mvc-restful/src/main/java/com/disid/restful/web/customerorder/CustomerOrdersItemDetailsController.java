package com.disid.restful.web.customerorder;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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

  public OrderDetailService orderDetailService;

  @Autowired
  public CustomerOrdersItemDetailsController(CustomerOrderService customerOrderService,
      OrderDetailService orderDetailService) {
    this.orderDetailService = orderDetailService;
    this.customerOrderService = customerOrderService;
  }

  @ModelAttribute
  public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id) {
    return customerOrderService.findOne(id);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<OrderDetail> listOrderDetails(
      @PathVariable("customerorder") CustomerOrder customerOrder, GlobalSearch search,
      Pageable pageable) {
    Page<OrderDetail> orderDetails =
        orderDetailService.findAllByCustomerOrder(customerOrder, search, pageable);
    return orderDetails;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
  @ResponseBody
  public DatatablesData<OrderDetail> listOrderDetails(
      @PathVariable("customerorder") CustomerOrder customerOrder, GlobalSearch search,
      Pageable pageable, @RequestParam("draw") Integer draw) {
    Page<OrderDetail> orderDetails =
        orderDetailService.findAllByCustomerOrder(customerOrder, search, pageable);
    long allAvailableOrderDetails =
        orderDetailService.countByCustomerOrderId(customerOrder.getId());
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
    return customerOrderService.deleteFromDetails(customerOrder, detail);
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
    return customerOrderService.deleteFromDetails(customerOrder, details);
  }

}

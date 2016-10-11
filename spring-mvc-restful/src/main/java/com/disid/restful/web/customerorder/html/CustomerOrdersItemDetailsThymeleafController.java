package com.disid.restful.web.customerorder.html;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.OrderDetailService;

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
@RequestMapping(value = "/customerorders/{customerorder}/details",
    produces = MediaType.TEXT_HTML_VALUE)
public class CustomerOrdersItemDetailsThymeleafController {

  public CustomerOrderService customerOrderService;
  public OrderDetailService orderDetailService;
  private MessageSource messageSource;

  @Autowired
  public CustomerOrdersItemDetailsThymeleafController(CustomerOrderService customerOrderService,
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

  @GetMapping(produces = Datatables.MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity<DatatablesData<OrderDetail>> listOrderDetails(
      @PathVariable("customerorder") CustomerOrder customerOrder, GlobalSearch search,
      Pageable pageable, @RequestParam(Datatables.PARAMETER_DRAW) Integer draw) {
    Page<OrderDetail> orderDetails =
        orderDetailService.findByCustomerOrder(customerOrder, search, pageable);
    long totalOrderDetailCount = orderDetails.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalOrderDetailCount = orderDetailService.countByCustomerOrder(customerOrder);
    }
    DatatablesData<OrderDetail> datatablesData =
        new DatatablesData<OrderDetail>(orderDetails, totalOrderDetailCount, draw);
    return ResponseEntity.status(HttpStatus.FOUND).body(datatablesData);
  }

}

package com.disid.restful.web.customerorder.html;

import com.disid.restful.datatables.Datatables;
import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.service.api.CustomerOrderService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping(value = "/customerorders/{customerorder}/details",
    produces = MediaType.TEXT_HTML_VALUE)
public class CustomerOrdersItemDetailsThymeleafController {

  public CustomerOrderService customerOrderService;

  @Autowired
  public CustomerOrdersItemDetailsThymeleafController(CustomerOrderService customerOrderService) {
    this.customerOrderService = customerOrderService;
  }

  @ModelAttribute("customerorder")
  public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id) {
    return customerOrderService.findOne(id);
  }

  @GetMapping(produces = Datatables.MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity<DatatablesData<OrderDetail>> listOrderDetails(
      @PathVariable("customerorder") CustomerOrder customerOrder, GlobalSearch search,
      Pageable pageable, @RequestParam("draw") Integer draw) {
    Page<OrderDetail> orderDetails =
        customerOrderService.findDetailsByCustomerOrder(customerOrder, search, pageable);
    long totalOrderDetailCount = orderDetails.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalOrderDetailCount = customerOrderService.countDetailsByCustomerOrder(customerOrder);
    }
    DatatablesData<OrderDetail> datatablesData =
        new DatatablesData<OrderDetail>(orderDetails, totalOrderDetailCount, draw);
    return ResponseEntity.status(HttpStatus.FOUND).body(datatablesData);
  }

}

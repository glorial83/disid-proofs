package com.disid.restful.web.customerorder.html;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;

import java.util.Collections;
import java.util.Locale;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/customerorders/{customerorder}/details",
    produces = MediaType.TEXT_HTML_VALUE, name = "CustomerOrdersItemDetailsThymeleafController")
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

  @GetMapping(value = "/dt", produces = Datatables.MEDIA_TYPE, name = "datatables")
  @ResponseBody
  public ResponseEntity<DatatablesData<OrderDetail>> datatables(
      @ModelAttribute CustomerOrder customerOrder, GlobalSearch search, Pageable pageable,
      @RequestParam(Datatables.PARAMETER_DRAW) Integer draw) {
    Page<OrderDetail> orderDetails =
        orderDetailService.findByCustomerOrder(customerOrder, search, pageable);
    long totalOrderDetailCount = orderDetails.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalOrderDetailCount = orderDetailService.countByCustomerOrder(customerOrder);
    }
    DatatablesData<OrderDetail> datatablesData =
        new DatatablesData<OrderDetail>(orderDetails, totalOrderDetailCount, draw);
    return ResponseEntity.ok(datatablesData);
  }

  @GetMapping(value = "/{orderdetail}/show", name = "show")
  public ModelAndView show(@ModelAttribute CustomerOrder customerOrder,
      @PathVariable("orderdetail") Integer id, Model model, Locale locale) {
    OrderDetailPK detailPk = new OrderDetailPK(customerOrder.getId(), id);
    OrderDetail orderDetail = orderDetailService.findOne(detailPk);
    if (orderDetail == null) {
      String message = messageSource.getMessage("error_orderDetailNotFound", null, locale);
      throw new NotFoundException(message);
    }
    return new ModelAndView("orderdetails/show");
  }

  @GetMapping(value = "/create-form", name = "createForm")
  public ModelAndView createForm(@ModelAttribute CustomerOrder customerOrder, Model model) {
    model.addAttribute(new OrderDetail());
    return new ModelAndView("orderdetails/create");
  }

  @PostMapping(name = "create")
  public ModelAndView create(@ModelAttribute CustomerOrder customerOrder,
      @Valid @ModelAttribute OrderDetail orderDetail, BindingResult result, Model model) {

    if (result.hasErrors()) {
      return new ModelAndView("orderdetails/create");
    }

    customerOrderService.addToDetails(customerOrder, Collections.singleton(orderDetail));

    UriComponents listURI = CustomerOrdersCollectionThymeleafController.listURI();
    return new ModelAndView("redirect:" + listURI.toUriString());
  }

  @GetMapping(value = "/{orderdetail}/edit-form", name = "editForm")
  public ModelAndView editForm(@ModelAttribute CustomerOrder customerOrder,
      @PathVariable("orderdetail") Integer detailId, Model model) {

    OrderDetailPK detailPK = new OrderDetailPK(customerOrder.getId(), detailId);
    OrderDetail detail = orderDetailService.findOne(detailPK);
    model.addAttribute(detail);
    
    return new ModelAndView("orderdetails/edit");
  }

  @PutMapping(value = "/{orderdetail}", name = "update")
  public ModelAndView update(@ModelAttribute CustomerOrder customerOrder,
      @PathVariable("orderdetail") Integer detailId, @Valid @ModelAttribute OrderDetail orderDetail,
      BindingResult result, Model model) {

    if (result.hasErrors()) {
      return new ModelAndView("orderdetails/edit");
    }

    OrderDetailPK detailPK = new OrderDetailPK(customerOrder.getId(), detailId);
    orderDetail.setId(detailPK);

    OrderDetail storedDetail = orderDetailService.findOne(detailPK);
    storedDetail.setQuantity(orderDetail.getQuantity());
    storedDetail.setProduct(orderDetail.getProduct());
    orderDetailService.save(storedDetail);

    UriComponents listURI = CustomerOrdersCollectionThymeleafController.listURI();
    return new ModelAndView("redirect:" + listURI.toUriString());
  }

  @DeleteMapping(value = "/{orderdetail}", name = "removeFromDetails")
  @ResponseBody
  public ResponseEntity<?> removeFromDetails(@ModelAttribute CustomerOrder customerOrder,
      @PathVariable("orderdetail") Integer detail) {
    customerOrderService.removeFromDetails(customerOrder, Collections.singleton(detail));
    return ResponseEntity.ok().build();
  }

}

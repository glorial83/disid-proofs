package com.disid.restful.service.impl;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.repository.CustomerOrderRepository;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RooServiceImpl(service = CustomerOrderService.class)
public class CustomerOrderServiceImpl {

  private OrderDetailService orderDetailService;

  public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;
  }

  @Autowired
  public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository,
      OrderDetailService orderDetailService) {
    this(customerOrderRepository);
    this.orderDetailService = orderDetailService;
  }

  public void delete(CustomerOrder customerOrder) {
    customerOrderRepository.delete(customerOrder);
  }

  public Set<CustomerOrder> findByIdIn(Long[] orders) {
    return customerOrderRepository.findByIdIn(orders);
  }

  @Transactional
  public CustomerOrder addToDetails(CustomerOrder customerOrder, OrderDetail... details) {
    customerOrder.addToDetails(details);
    return customerOrderRepository.save(customerOrder);
  }

  @Transactional
  public CustomerOrder deleteFromDetails(CustomerOrder customerOrder, OrderDetail... details) {
    customerOrder.removeFromDetails(details);
    return customerOrderRepository.save(customerOrder);
  }

}

package com.disid.restful.service.impl;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.repository.CustomerOrderRepository;
import com.disid.restful.service.api.CustomerOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;

@RooServiceImpl(service = CustomerOrderService.class)
public class CustomerOrderServiceImpl {

  @Autowired
  public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;
  }

  public void delete(CustomerOrder customerOrder) {
    customerOrderRepository.delete(customerOrder);
  }

  public Set<CustomerOrder> findByIdIn(Long[] orders) {
    return customerOrderRepository.findByIdIn(orders);
  }

  @Transactional
  public CustomerOrder addToDetails(CustomerOrder customerOrder, OrderDetail... details) {
    customerOrder.addToDetails(Arrays.asList(details));
    return customerOrderRepository.save(customerOrder);
  }

  @Transactional
  public CustomerOrder deleteFromDetails(CustomerOrder customerOrder, OrderDetail... details) {
    customerOrder.removeFromDetails(Arrays.asList(details));
    return customerOrderRepository.save(customerOrder);
  }

}

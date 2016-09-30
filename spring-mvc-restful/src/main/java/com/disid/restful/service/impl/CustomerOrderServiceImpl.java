package com.disid.restful.service.impl;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.repository.CustomerOrderRepository;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CustomerOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RooServiceImpl(service = CustomerOrderService.class)
public class CustomerOrderServiceImpl {

  @Autowired
  public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;
  }

  public void delete(CustomerOrder customerOrder) {
    customerOrderRepository.delete(customerOrder);
  }

  @Transactional
  public CustomerOrder addToDetails(CustomerOrder customerOrder, OrderDetail... details) {
    customerOrder.addToDetails(Arrays.asList(details));
    customerOrderRepository.save(customerOrder);
    return findOne(customerOrder.getId());
  }

  @Transactional
  public CustomerOrder removeFromDetails(CustomerOrder customerOrder, OrderDetail... details) {
    customerOrder.removeFromDetails(Arrays.asList(details));
    customerOrderRepository.save(customerOrder);
    return findOne(customerOrder.getId());
  }

  public List<CustomerOrder> findAll(Long... ids) {
    return customerOrderRepository.findAll(Arrays.asList(ids));
  }

  public Page<OrderDetail> findDetailsByCustomerOrder(CustomerOrder customerOrderField,
      GlobalSearch globalSearch, Pageable pageable) {
    return customerOrderRepository.findDetailsByCustomerOrder(customerOrderField,
        globalSearch, pageable);
  }

  public long countDetailsByCustomerOrder(CustomerOrder customerOrderField) {
    return customerOrderRepository.countDetailsByCustomerOrder(customerOrderField);
  }

  public OrderDetail findOneOrderDetail(OrderDetailPK orderDetailPK) {
    return customerOrderRepository.findOneOrderDetail(orderDetailPK);
  }
}

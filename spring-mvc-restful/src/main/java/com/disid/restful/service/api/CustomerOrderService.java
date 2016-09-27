package com.disid.restful.service.api;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;

import org.springframework.roo.addon.layers.service.annotations.RooService;

import java.util.Set;

@RooService(entity = CustomerOrder.class)
public interface CustomerOrderService {

  void delete(CustomerOrder customerOrder);

  Set<CustomerOrder> findByIdIn(Long[] orders);

  CustomerOrder addToDetails(CustomerOrder customerOrder, OrderDetail... details);

  CustomerOrder deleteFromDetails(CustomerOrder customerOrder, OrderDetail... details);



}

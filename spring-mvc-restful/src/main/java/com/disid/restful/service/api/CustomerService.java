package com.disid.restful.service.api;

import org.springframework.roo.addon.layers.service.annotations.RooService;

import com.disid.restful.model.Customer;

@RooService(entity = Customer.class)
public interface CustomerService {

  void delete(Customer customer);

  Customer addToOrders(Customer customer, Long... orders);

  Customer deleteFromOrders(Customer customer, Long... orders);
}

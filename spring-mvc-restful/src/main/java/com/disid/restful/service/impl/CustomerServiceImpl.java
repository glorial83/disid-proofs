package com.disid.restful.service.impl;

import com.disid.restful.model.Address;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.repository.CustomerRepository;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RooServiceImpl(service = CustomerService.class)
public class CustomerServiceImpl {

  private CustomerOrderService customerOrderService;

  private CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Autowired
  public CustomerServiceImpl(CustomerRepository customerRepository,
      @Lazy CustomerOrderService customerOrderService) {
    this(customerRepository);
    this.customerOrderService = customerOrderService;
  }

  public void delete(Customer customer) {
    customerRepository.delete(customer);
  }

  @Transactional
  public Customer addToOrders(Customer customer, Long... orders) {
    List<CustomerOrder> customerOrders = customerOrderService.findAll(Arrays.asList(orders));
    customer.addToOrders(customerOrders);
    return customerRepository.save(customer);
  }

  @Transactional
  public Customer deleteFromOrders(Customer customer, Long... orders) {
    List<CustomerOrder> customerOrders = customerOrderService.findAll(Arrays.asList(orders));
    customer.removeFromOrders(customerOrders);
    return customerRepository.save(customer);
  }

  @Transactional
  public Customer setAddress(Customer customer, Address address) {
    customer.setAddress(address);
    return customerRepository.save(customer);
  }

  @Transactional
  public Customer removeAddress(Customer customer) {
    customer.removeAddress();
    return customerRepository.save(customer);
  }
}

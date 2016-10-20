package com.disid.restful.service.impl;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.CustomerSearchForm;
import com.disid.restful.repository.CustomerRepository;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.CustomerService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

  private CustomerOrderService customerOrderService;

  @Autowired
  public CustomerServiceImpl(CustomerRepository customerRepository,
      @Lazy CustomerOrderService customerOrderService) {
    this.customerRepository = customerRepository;
    this.customerOrderService = customerOrderService;
  }

  @Transactional
  public void delete(Customer customer) {
    // Clear bidirectional one-to-many parent relationship with CustomerOrders
    for (CustomerOrder order : customer.getOrders()) {
      order.setCustomer(null);
    }
    customer.setOrders(null);

    // Clear bidirectional one-to-one parent relationship with Address
    customer.removeFromAddress();
    customerRepository.delete(customer);
  }

  @Transactional
  public Customer addToOrders(Customer customer, Iterable<Long> orders) {
    List<CustomerOrder> customerOrders = customerOrderService.findAll(orders);
    customer.addToOrders(customerOrders);
    return customerRepository.save(customer);
  }

  @Transactional
  @Override
  public Customer removeFromOrders(Customer customer, Iterable<Long> orders) {
    List<CustomerOrder> customerOrders = customerOrderService.findAll(orders);
    customer.removeFromOrders(customerOrders);
    return customerRepository.save(customer);
  }

  public Page<Customer> findByFirstNameLastName(CustomerSearchForm formBean, GlobalSearch search,
      Pageable pageable) {
    return customerRepository.findByFirstNameLastName(formBean, search, pageable);
  }

  public long countByFirstNameLastName(CustomerSearchForm formBean) {
    return customerRepository.countByFirstNameLastName(formBean);
  }


  public CustomerRepository customerRepository;

  @Transactional(readOnly = false)
  public Customer save(Customer entity) {
    // Ensure the relationship is maintained
    entity.addToAddress(entity.getAddress());
    return customerRepository.save(entity);
  }

  @Transactional(readOnly = false)
  public List<Customer> save(Iterable<Customer> entities) {
    return customerRepository.save(entities);
  }

  @Transactional(readOnly = false)
  public void delete(Iterable<Long> ids) {
    List<Customer> toDelete = customerRepository.findAll(ids);
    customerRepository.deleteInBatch(toDelete);
  }

  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  public List<Customer> findAll(Iterable<Long> ids) {
    return customerRepository.findAll(ids);
  }

  public Customer findOne(Long id) {
    return customerRepository.findOne(id);
  }

  public long count() {
    return customerRepository.count();
  }

  public Page<Customer> findAll(GlobalSearch globalSearch, Pageable pageable) {
    return customerRepository.findAll(globalSearch, pageable);
  }

}

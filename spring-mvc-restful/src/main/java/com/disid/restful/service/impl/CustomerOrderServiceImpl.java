package com.disid.restful.service.impl;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.repository.CustomerOrderRepository;
import com.disid.restful.service.api.CustomerOrderService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerOrderServiceImpl implements CustomerOrderService {

  private CustomerOrderRepository customerOrderRepository;

  @Autowired
  public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;
  }

  @Transactional
  public void delete(CustomerOrder customerOrder) {
    // Clear bidirectional many-to-one child relationship with Customer
    if (customerOrder.getCustomer() != null) {
      customerOrder.getCustomer().getOrders().remove(customerOrder);
    }
    customerOrder.setCustomer(null);

    // Clear bidirectional one-to-many parent relationship with OrderDetails
    for (OrderDetail detail : customerOrder.getDetails()) {
      detail.setCustomerOrder(null);
    }

    customerOrderRepository.delete(customerOrder);
  }

  @Transactional
  public CustomerOrder addToDetails(CustomerOrder customerOrder, Iterable<OrderDetail> details) {
    customerOrder.addToDetails(details);
    customerOrderRepository.save(customerOrder);
    return findOne(customerOrder.getId());
  }

  @Transactional
  public CustomerOrder removeFromDetails(CustomerOrder customerOrder,
      Iterable<OrderDetail> details) {
    customerOrder.removeFromDetails(details);
    customerOrderRepository.save(customerOrder);
    return findOne(customerOrder.getId());
  }

  @Transactional(readOnly = false)
  public CustomerOrder save(CustomerOrder entity) {
    return customerOrderRepository.save(entity);
  }

  @Transactional(readOnly = false)
  public List<CustomerOrder> save(Iterable<CustomerOrder> entities) {
    return customerOrderRepository.save(entities);
  }

  @Transactional(readOnly = false)
  public void delete(Iterable<Long> ids) {
    List<CustomerOrder> toDelete = customerOrderRepository.findAll(ids);
    customerOrderRepository.deleteInBatch(toDelete);
  }

  public List<CustomerOrder> findAll() {
    return customerOrderRepository.findAll();
  }

  public List<CustomerOrder> findAll(Iterable<Long> ids) {
    return customerOrderRepository.findAll(ids);
  }

  public CustomerOrder findOne(Long id) {
    return customerOrderRepository.findOne(id);
  }

  public long count() {
    return customerOrderRepository.count();
  }

  public Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable) {
    return customerOrderRepository.findAll(globalSearch, pageable);
  }

  public Long countByCustomer(Customer customer) {
    return customerOrderRepository.countByCustomer(customer);
  }

  public Page<CustomerOrder> findByCustomer(Customer customer, GlobalSearch globalSearch,
      Pageable pageable) {
    return customerOrderRepository.findByCustomer(customer, globalSearch, pageable);
  }
}

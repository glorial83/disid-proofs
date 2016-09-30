package com.disid.restful.service.impl;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.repository.CustomerOrderRepository;
import com.disid.restful.repository.OrderDetailRepository;
import com.disid.restful.service.api.CustomerOrderService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerOrderServiceImpl implements CustomerOrderService {

  private CustomerOrderRepository customerOrderRepository;
  private OrderDetailRepository orderDetailRepository;

  @Autowired
  public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository,
      OrderDetailRepository orderDetailRepository) {
    this.customerOrderRepository = customerOrderRepository;
    this.orderDetailRepository = orderDetailRepository;
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
    return orderDetailRepository.findByCustomerOrder(customerOrderField, globalSearch,
        pageable);
  }

  public long countDetailsByCustomerOrder(CustomerOrder customerOrderField) {
    return orderDetailRepository.countByCustomerOrder(customerOrderField);
  }

  public OrderDetail findOneOrderDetail(OrderDetailPK orderDetailPK) {
    return orderDetailRepository.findOne(orderDetailPK);
  }

  @Transactional(readOnly = false)
  public CustomerOrder save(CustomerOrder entity) {
    return customerOrderRepository.save(entity);
  }

  @Transactional(readOnly = false)
  public void delete(Long id) {
    customerOrderRepository.delete(id);
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

  public Long countByCustomerId(Long id) {
    return customerOrderRepository.countByCustomerId(id);
  }

  public Page<CustomerOrder> findAllByCustomer(Customer customerField, GlobalSearch globalSearch,
      Pageable pageable) {
    return customerOrderRepository.findAllByCustomer(customerField, globalSearch, pageable);
  }
}

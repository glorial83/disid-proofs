package com.disid.restful.service.api;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerOrderService {

  void delete(CustomerOrder customerOrder);

  CustomerOrder addToDetails(CustomerOrder customerOrder, OrderDetail... details);

  CustomerOrder removeFromDetails(CustomerOrder customerOrder, OrderDetail... details);

  CustomerOrder save(CustomerOrder entity);

  void delete(Long id);

  List<CustomerOrder> save(Iterable<CustomerOrder> entities);

  void delete(Iterable<Long> ids);

  List<CustomerOrder> findAll();

  List<CustomerOrder> findAll(Iterable<Long> ids);

  CustomerOrder findOne(Long id);

  long count();

  Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable);

  Page<CustomerOrder> findByCustomer(Customer customer, GlobalSearch globalSearch,
      Pageable pageable);

  Long countByCustomer(Customer customer);
}

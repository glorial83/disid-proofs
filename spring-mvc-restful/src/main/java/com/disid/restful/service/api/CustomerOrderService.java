package com.disid.restful.service.api;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerOrderService {

  CustomerOrder addToDetails(CustomerOrder customerOrder, Iterable<OrderDetail> details);

  long count();

  Long countByCustomer(Customer customer);

  void delete(CustomerOrder customerOrder);

  void delete(Iterable<Long> ids);

  List<CustomerOrder> findAll();

  Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable);

  List<CustomerOrder> findAll(Iterable<Long> ids);

  Page<CustomerOrder> findByCustomer(Customer customer, GlobalSearch globalSearch,
      Pageable pageable);

  CustomerOrder findOne(Long id);

  CustomerOrder removeFromDetails(CustomerOrder customerOrder, Iterable<OrderDetail> details);

  CustomerOrder save(CustomerOrder entity);

  List<CustomerOrder> save(Iterable<CustomerOrder> entities);
}

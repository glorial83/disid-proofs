package com.disid.restful.service.api;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.repository.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerOrderService {

  void delete(CustomerOrder customerOrder);

  List<CustomerOrder> findAll(Long... orders);

  CustomerOrder addToDetails(CustomerOrder customerOrder, OrderDetail... details);

  CustomerOrder removeFromDetails(CustomerOrder customerOrder, OrderDetail... details);

  Page<OrderDetail> findDetailsByCustomerOrder(CustomerOrder customerOrderField,
      GlobalSearch globalSearch, Pageable pageable);

  long countDetailsByCustomerOrder(CustomerOrder customerOrderField);

  OrderDetail findOneOrderDetail(OrderDetailPK orderDetailPK);

  CustomerOrder save(CustomerOrder entity);

  void delete(Long id);

  List<CustomerOrder> save(Iterable<CustomerOrder> entities);

  void delete(Iterable<Long> ids);

  List<CustomerOrder> findAll();

  List<CustomerOrder> findAll(Iterable<Long> ids);

  CustomerOrder findOne(Long id);

  long count();

  Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable);

  Page<CustomerOrder> findAllByCustomer(Customer customerField, GlobalSearch globalSearch,
      Pageable pageable);

  Long countByCustomerId(Long id);
}

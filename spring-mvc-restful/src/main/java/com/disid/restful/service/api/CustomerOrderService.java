package com.disid.restful.service.api;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.repository.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooService;

import java.util.List;

@RooService(entity = CustomerOrder.class)
public interface CustomerOrderService {

  void delete(CustomerOrder customerOrder);

  List<CustomerOrder> findAll(Long... orders);

  CustomerOrder addToDetails(CustomerOrder customerOrder, OrderDetail... details);

  CustomerOrder deleteFromDetails(CustomerOrder customerOrder, OrderDetail... details);

  Page<OrderDetail> findDetailsByCustomerOrder(CustomerOrder customerOrderField,
      GlobalSearch globalSearch, Pageable pageable);

  long countDetailsByCustomerOrder(CustomerOrder customerOrderField);

  OrderDetail findOneOrderDetail(OrderDetailPK orderDetailPK);

}

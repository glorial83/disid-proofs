package com.disid.restful.service.api;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDetailService {

  long countByCustomerOrder(CustomerOrder customerOrderField);

  Page<OrderDetail> findByCustomerOrder(CustomerOrder customerOrderField, GlobalSearch globalSearch,
      Pageable pageable);

  OrderDetail findOne(OrderDetailPK orderDetailPK);

  List<OrderDetail> findAll(Iterable<OrderDetailPK> detailPks);

  OrderDetail save(OrderDetail orderDetail);

}

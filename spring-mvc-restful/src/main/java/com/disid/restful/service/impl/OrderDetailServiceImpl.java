package com.disid.restful.service.impl;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.repository.OrderDetailRepository;
import com.disid.restful.service.api.OrderDetailService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderDetailServiceImpl implements OrderDetailService {

  private OrderDetailRepository orderDetailRepository;

  @Autowired
  public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
    this.orderDetailRepository = orderDetailRepository;
  }

  public Page<OrderDetail> findByCustomerOrder(CustomerOrder customerOrderField,
      GlobalSearch globalSearch, Pageable pageable) {
    return orderDetailRepository.findByCustomerOrder(customerOrderField, globalSearch,
        pageable);
  }

  public long countByCustomerOrder(CustomerOrder customerOrderField) {
    return orderDetailRepository.countByCustomerOrder(customerOrderField);
  }

  public OrderDetail findOne(OrderDetailPK orderDetailPK) {
    return orderDetailRepository.findOne(orderDetailPK);
  }

}

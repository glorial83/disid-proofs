package com.disid.restful.repository;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDetailRepositoryCustom {

  Page<OrderDetail> findByCustomerOrder(CustomerOrder customerOrderField,
      GlobalSearch globalSearch, Pageable pageable);

}

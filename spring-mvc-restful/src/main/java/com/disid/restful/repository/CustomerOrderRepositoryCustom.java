package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerOrderRepositoryCustom {

  Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable);

  Page<CustomerOrder> findByCustomer(Customer customer, GlobalSearch globalSearch,
      Pageable pageable);
}

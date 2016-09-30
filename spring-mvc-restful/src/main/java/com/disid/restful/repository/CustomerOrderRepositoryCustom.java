package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;

import io.springlets.data.jpa.repository.support.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerOrderRepositoryCustom {

  Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable);

  Page<CustomerOrder> findAllByCustomer(Customer customerField, GlobalSearch globalSearch,
      Pageable pageable);
}

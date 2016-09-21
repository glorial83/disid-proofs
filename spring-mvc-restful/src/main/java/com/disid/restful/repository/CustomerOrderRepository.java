package com.disid.restful.repository;

import java.util.Set;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

import com.disid.restful.model.CustomerOrder;

@RooJpaRepository(entity = CustomerOrder.class)
public interface CustomerOrderRepository {

  Set<CustomerOrder> findByIdIn(Long[] orders);
}

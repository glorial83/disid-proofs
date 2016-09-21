package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

@RooJpaRepository(entity = Customer.class)
public interface CustomerRepository {
}

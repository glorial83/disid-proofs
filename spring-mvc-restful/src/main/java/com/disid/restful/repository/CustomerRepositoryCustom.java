package com.disid.restful.repository;

import com.disid.restful.model.Customer;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = Customer.class, defaultSearchResult = Customer.class)
public interface CustomerRepositoryCustom {
}

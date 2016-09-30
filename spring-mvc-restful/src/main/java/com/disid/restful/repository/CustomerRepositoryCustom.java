package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerSearchForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = Customer.class, defaultSearchResult = Customer.class)
public interface CustomerRepositoryCustom {

  Page<Customer> findByFirstNameLastName(CustomerSearchForm formBean, GlobalSearch search,
      Pageable pageable);

  long countByFirstNameLastName(CustomerSearchForm formBean);
}

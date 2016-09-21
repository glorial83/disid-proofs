package com.disid.restful.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.restful.model.Customer;
import com.disid.restful.model.QCustomer;
import com.mysema.query.jpa.JPQLQuery;

@RooJpaRepositoryCustomImpl(repository = CustomerRepositoryCustom.class)
public class CustomerRepositoryImpl extends QueryDslRepositorySupport {

  CustomerRepositoryImpl() {
    super(Customer.class);
  }

  private JPQLQuery getQueryFrom(QCustomer qEntity) {
    return from(qEntity);
  }
}

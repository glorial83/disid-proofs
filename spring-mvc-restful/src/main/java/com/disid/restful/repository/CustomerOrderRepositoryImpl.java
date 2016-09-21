package com.disid.restful.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.QCustomerOrder;
import com.mysema.query.jpa.JPQLQuery;

@RooJpaRepositoryCustomImpl(repository = CustomerOrderRepositoryCustom.class)
public class CustomerOrderRepositoryImpl extends QueryDslRepositorySupport {

  CustomerOrderRepositoryImpl() {
    super(CustomerOrder.class);
  }

  private JPQLQuery getQueryFrom(QCustomerOrder qEntity) {
    return from(qEntity);
  }
}

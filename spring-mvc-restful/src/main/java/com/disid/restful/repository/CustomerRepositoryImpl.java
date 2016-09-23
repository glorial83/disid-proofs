package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.QCustomer;
import com.mysema.query.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;

@RooJpaRepositoryCustomImpl(repository = CustomerRepositoryCustom.class)
public class CustomerRepositoryImpl extends QueryDslRepositorySupportExt<Customer>
    implements CustomerRepositoryCustom {

  CustomerRepositoryImpl() {
    super(Customer.class);
  }

  private JPQLQuery getQueryFrom(QCustomer qEntity) {
    return from(qEntity);
  }

  public Page<Customer> findAll(GlobalSearch globalSearch, Pageable pageable) {
    QCustomer customer = QCustomer.customer;
    JPQLQuery query = from(customer).leftJoin(customer.address).fetch();

    applyGlobalSearch(globalSearch, query, customer.firstName, customer.lastName,
        customer.address.street, customer.address.city, customer.address.streetNumber);
    applyPagination(pageable, query);
    applyOrderById(query);

    return loadPage(query, pageable, customer);
  }
}

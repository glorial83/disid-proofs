package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.QCustomerOrder;
import com.mysema.query.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;

@RooJpaRepositoryCustomImpl(repository = CustomerOrderRepositoryCustom.class)
public class CustomerOrderRepositoryImpl extends QueryDslRepositorySupportExt<CustomerOrder> {

  CustomerOrderRepositoryImpl() {
    super(CustomerOrder.class);
  }

  private JPQLQuery getQueryFrom(QCustomerOrder qEntity) {
    return from(qEntity);
  }

  public Page<CustomerOrder> findAllByCustomer(Customer customer, GlobalSearch globalSearch,
      Pageable pageable) {
    QCustomerOrder customerOrder = QCustomerOrder.customerOrder;
    JPQLQuery query = from(customerOrder);

    if (customer != null) {
      query.where(customerOrder.customer.eq(customer));
    }

    applyGlobalSearch(globalSearch, query, customerOrder.shipAddress);
    applyPagination(pageable, query);
    applyOrderById(query);

    return loadPage(query, pageable, customerOrder);
  }
}

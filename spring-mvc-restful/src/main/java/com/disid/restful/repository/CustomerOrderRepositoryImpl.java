package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.QCustomerOrder;
import com.querydsl.jpa.JPQLQuery;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class CustomerOrderRepositoryImpl extends QueryDslRepositorySupportExt<CustomerOrder>
    implements CustomerOrderRepositoryCustom {

  CustomerOrderRepositoryImpl() {
    super(CustomerOrder.class);
  }

  public Page<CustomerOrder> findAllByCustomer(Customer customer, GlobalSearch globalSearch,
      Pageable pageable) {
    QCustomerOrder customerOrder = QCustomerOrder.customerOrder;
    JPQLQuery<CustomerOrder> query = from(customerOrder);
    if (customer != null) {
      query.where(customerOrder.customer.eq(customer));
    }
    applyGlobalSearch(globalSearch, query, customerOrder.shipAddress);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, customerOrder);
  }

  public Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable) {
    QCustomerOrder customerOrder = QCustomerOrder.customerOrder;
    JPQLQuery<CustomerOrder> query = from(customerOrder);
    applyGlobalSearch(globalSearch, query, customerOrder.shipAddress);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, customerOrder);
  }
}

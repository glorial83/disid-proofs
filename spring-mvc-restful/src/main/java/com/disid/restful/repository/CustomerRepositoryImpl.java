package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerSearchForm;
import com.disid.restful.model.QCustomer;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional(readOnly = true)
public class CustomerRepositoryImpl extends QueryDslRepositorySupportExt<Customer>
    implements CustomerRepositoryCustom {

  CustomerRepositoryImpl() {
    super(Customer.class);
  }

  public Page<Customer> findAll(GlobalSearch globalSearch, Pageable pageable) {
    QCustomer customer = QCustomer.customer;
    JPQLQuery<Customer> query = from(customer).leftJoin(customer.address).fetchJoin();
    applyGlobalSearch(globalSearch, query, customer.firstName, customer.lastName,
        customer.address.street, customer.address.city, customer.address.streetNumber);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, customer);
  }

  public Page<Customer> findByFirstNameLastName(CustomerSearchForm formBean,
      GlobalSearch globalSearch, Pageable pageable) {
    QCustomer customer = QCustomer.customer;
    JPQLQuery<Customer> query = from(customer).leftJoin(customer.address).fetchJoin();
    applySearchForm(formBean, query);
    applyGlobalSearch(globalSearch, query, customer.firstName, customer.lastName,
        customer.address.street, customer.address.city, customer.address.streetNumber);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, customer);
  }

  public long countByFirstNameLastName(CustomerSearchForm formBean) {
    QCustomer customer = QCustomer.customer;
    JPQLQuery<Customer> query = from(customer);
    applySearchForm(formBean, query);
    return query.fetchCount();
  }

  private void applySearchForm(CustomerSearchForm formBean, JPQLQuery<Customer> query) {
    QCustomer customer = QCustomer.customer;
    BooleanBuilder searchFormCondition = new BooleanBuilder();
    if (StringUtils.hasText(formBean.getFirstName())) {
      searchFormCondition.and(customer.firstName.containsIgnoreCase(formBean.getFirstName()));
    }
    if (StringUtils.hasText(formBean.getLastName())) {
      searchFormCondition.and(customer.lastName.containsIgnoreCase(formBean.getLastName()));
    }
    query.where(searchFormCondition);
  }

}

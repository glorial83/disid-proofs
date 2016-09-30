package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerSearchForm;
import com.disid.restful.model.QCustomer;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import org.springframework.util.StringUtils;

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

  public Page<Customer> findByFirstNameLastName(CustomerSearchForm formBean,
      GlobalSearch globalSearch, Pageable pageable) {

    QCustomer customer = QCustomer.customer;
    JPQLQuery query = from(customer).leftJoin(customer.address).fetch();

    applySearchForm(formBean, query);

    applyGlobalSearch(globalSearch, query, customer.firstName, customer.lastName,
        customer.address.street, customer.address.city, customer.address.streetNumber);
    applyPagination(pageable, query);
    applyOrderById(query);

    return loadPage(query, pageable, customer);
  }

  public long countByFirstNameLastName(CustomerSearchForm formBean) {

    QCustomer customer = QCustomer.customer;
    JPQLQuery query = from(customer);

    applySearchForm(formBean, query);

    return query.count();
  }

  private void applySearchForm(CustomerSearchForm formBean, JPQLQuery query) {
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

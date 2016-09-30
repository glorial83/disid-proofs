package com.disid.restful.repository;
import com.disid.restful.model.Address;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerSearchForm;
import com.disid.restful.model.QCustomer;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.NumberPath;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional(readOnly = true)
public class CustomerRepositoryImpl extends QueryDslRepositorySupportExt<Customer> implements CustomerRepositoryCustom {

    CustomerRepositoryImpl() {
        super(Customer.class);
    }

    private JPQLQuery getQueryFrom(QCustomer qEntity) {
        return from(qEntity);
    }

    public Page<Customer> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QCustomer customer = QCustomer.customer;
        JPQLQuery query = from(customer).leftJoin(customer.address).fetch();
        applyGlobalSearch(globalSearch, query, customer.firstName, customer.lastName, customer.address.street, customer.address.city, customer.address.streetNumber);
        applyPagination(pageable, query);
        applyOrderById(query);
        return loadPage(query, pageable, customer);
    }

    public Page<Customer> findByFirstNameLastName(CustomerSearchForm formBean, GlobalSearch globalSearch, Pageable pageable) {
        QCustomer customer = QCustomer.customer;
        JPQLQuery query = from(customer).leftJoin(customer.address).fetch();
        applySearchForm(formBean, query);
        applyGlobalSearch(globalSearch, query, customer.firstName, customer.lastName, customer.address.street, customer.address.city, customer.address.streetNumber);
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

    public Page<Customer> findAllByAddress(Address addressField, GlobalSearch globalSearch, Pageable pageable) {
        NumberPath<Long> idCustomer = new NumberPath<Long>(Long.class, "id");
        QCustomer customer = QCustomer.customer;
        JPQLQuery query = getQueryFrom(customer);
        BooleanBuilder where = new BooleanBuilder(customer.address.eq(addressField));
        if (globalSearch != null) {
            String txt = globalSearch.getText();
            where.and(customer.firstName.containsIgnoreCase(txt).or(customer.lastName.containsIgnoreCase(txt)));
        }
        query.where(where);
        long totalFound = query.count();
        if (pageable != null) {
            if (pageable.getSort() != null) {
                for (Sort.Order order : pageable.getSort()) {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                    switch(((order.getProperty()))) {
                        case "firstName":
                            query.orderBy(new OrderSpecifier<String>(direction, customer.firstName));
                            break;
                        case "lastName":
                            query.orderBy(new OrderSpecifier<String>(direction, customer.lastName));
                            break;
                    }
                }
            }
            query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        }
        query.orderBy(idCustomer.asc());
        List<Customer> results = query.list(customer);
        return new PageImpl<Customer>(results, pageable, totalFound);
    }
}

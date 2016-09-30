package com.disid.restful.repository;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.QCustomerOrder;
import com.disid.restful.model.QOrderDetail;
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

import java.util.List;

@Transactional(readOnly = true)
public class CustomerOrderRepositoryImpl extends QueryDslRepositorySupportExt<CustomerOrder> implements CustomerOrderRepositoryCustom {

    CustomerOrderRepositoryImpl() {
        super(CustomerOrder.class);
    }

    private JPQLQuery getQueryFrom(QCustomerOrder qEntity) {
        return from(qEntity);
    }

    public Page<CustomerOrder> findAllByCustomer(Customer customer, GlobalSearch globalSearch, Pageable pageable) {
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

    public Page<OrderDetail> findDetailsByCustomerOrder(CustomerOrder customerOrder, GlobalSearch globalSearch, Pageable pageable) {
        QOrderDetail detail = QOrderDetail.orderDetail;
        JPQLQuery query = from(detail).where(detail.customerOrder.eq(customerOrder)).leftJoin(detail.product).fetch();
        applyGlobalSearch(globalSearch, query, detail.quantity, detail.product.name);
        applyPagination(pageable, query);
        applyOrderById(query, OrderDetail.class);
        return loadPage(query, pageable, detail);
    }

    public Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable) {
        NumberPath<Long> idCustomerOrder = new NumberPath<Long>(Long.class, "id");
        QCustomerOrder customerOrder = QCustomerOrder.customerOrder;
        JPQLQuery query = getQueryFrom(customerOrder);
        BooleanBuilder where = new BooleanBuilder();
        if (globalSearch != null) {
            String txt = globalSearch.getText();
            where.and(customerOrder.shipAddress.containsIgnoreCase(txt));
        }
        query.where(where);
        long totalFound = query.count();
        if (pageable != null) {
            if (pageable.getSort() != null) {
                for (Sort.Order order : pageable.getSort()) {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                    switch(((order.getProperty()))) {
                        case "shipAddress":
                            query.orderBy(new OrderSpecifier<String>(direction, customerOrder.shipAddress));
                            break;
                    }
                }
            }
            query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        }
        query.orderBy(idCustomerOrder.asc());
        List<CustomerOrder> results = query.list(customerOrder);
        return new PageImpl<CustomerOrder>(results, pageable, totalFound);
    }
}

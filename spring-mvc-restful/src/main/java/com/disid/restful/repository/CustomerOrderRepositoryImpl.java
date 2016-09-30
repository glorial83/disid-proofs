package com.disid.restful.repository;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.QCustomerOrder;
import com.disid.restful.model.QOrderDetail;
import com.mysema.query.jpa.JPQLQuery;

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
    JPQLQuery query = from(customerOrder);
    if (customer != null) {
      query.where(customerOrder.customer.eq(customer));
    }
    applyGlobalSearch(globalSearch, query, customerOrder.shipAddress);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, customerOrder);
  }

  public Page<OrderDetail> findDetailsByCustomerOrder(CustomerOrder customerOrder,
      GlobalSearch globalSearch, Pageable pageable) {
    QOrderDetail detail = QOrderDetail.orderDetail;
    JPQLQuery query =
        from(detail).where(detail.customerOrder.eq(customerOrder)).leftJoin(detail.product).fetch();
    applyGlobalSearch(globalSearch, query, detail.quantity, detail.product.name);
    applyPagination(pageable, query);
    applyOrderById(query, OrderDetail.class);
    return loadPage(query, pageable, detail);
  }

  public Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable) {
    QCustomerOrder customerOrder = QCustomerOrder.customerOrder;
    JPQLQuery query = from(customerOrder);
    applyGlobalSearch(globalSearch, query, customerOrder.shipAddress);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, customerOrder);
  }
}

package com.disid.restful.repository;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.QOrderDetail;
import com.querydsl.jpa.JPQLQuery;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class OrderDetailRepositoryImpl extends QueryDslRepositorySupportExt<OrderDetail>
    implements OrderDetailRepositoryCustom {

  OrderDetailRepositoryImpl() {
    super(OrderDetail.class);
  }

  public Page<OrderDetail> findByCustomerOrder(CustomerOrder customerOrder,
      GlobalSearch globalSearch, Pageable pageable) {
    QOrderDetail detail = QOrderDetail.orderDetail;
    JPQLQuery<OrderDetail> query =
        from(detail).where(detail.customerOrder.eq(customerOrder)).leftJoin(detail.product)
            .fetchJoin();
    applyGlobalSearch(globalSearch, query, detail.quantity, detail.product.name);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, detail);
  }

}

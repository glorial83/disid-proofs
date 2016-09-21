package com.disid.restful.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import org.springframework.transaction.annotation.Transactional;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.Product;
import com.disid.restful.model.QOrderDetail;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;

@RooJpaRepositoryCustomImpl(repository = OrderDetailRepositoryCustom.class)
@Transactional(readOnly = true)
public class OrderDetailRepositoryImpl extends QueryDslRepositorySupport
    implements OrderDetailRepositoryCustom {

  OrderDetailRepositoryImpl() {
    super(OrderDetail.class);
  }

  private JPQLQuery getQueryFrom(QOrderDetail qEntity) {
    return from(qEntity);
  }

  public Page<OrderDetail> findAll(GlobalSearch globalSearch, Pageable pageable) {
    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    JPQLQuery query = getQueryFrom(orderDetail);
    BooleanBuilder where = new BooleanBuilder();
    if (globalSearch != null) {
      String txt = globalSearch.getText();
      where.and(orderDetail.quantity.like("%".concat(txt).concat("%")));
    }
    query.where(where);
    long totalFound = query.count();
    if (pageable != null) {
      if (pageable.getSort() != null) {
        for (Sort.Order order : pageable.getSort()) {
          Order direction = order.isAscending() ? Order.ASC : Order.DESC;
          switch (((order.getProperty()))) {
            case "quantity":
              query.orderBy(new OrderSpecifier<Integer>(direction, orderDetail.quantity));
              break;
          }
        }
      }
      query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }
    // query.orderBy(orderDe.asc());
    List<OrderDetail> results = query.list(orderDetail);
    return new PageImpl<OrderDetail>(results, pageable, totalFound);
  }

  public Page<OrderDetail> findAllByProduct(Product productField, GlobalSearch globalSearch,
      Pageable pageable) {
    // NumberPath<OrderDetailPK> idOrderDetail = new
    // NumberPath<OrderDetailPK>(OrderDetailPK.class, "id");
    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    JPQLQuery query = getQueryFrom(orderDetail);
    BooleanBuilder where = new BooleanBuilder(orderDetail.product.eq(productField));
    if (globalSearch != null) {
      String txt = globalSearch.getText();
      where.and(orderDetail.quantity.like("%".concat(txt).concat("%")));
    }
    query.where(where);
    long totalFound = query.count();
    if (pageable != null) {
      if (pageable.getSort() != null) {
        for (Sort.Order order : pageable.getSort()) {
          Order direction = order.isAscending() ? Order.ASC : Order.DESC;
          switch (((order.getProperty()))) {
            case "quantity":
              query.orderBy(new OrderSpecifier<Integer>(direction, orderDetail.quantity));
              break;
          }
        }
      }
      query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }
    // query.orderBy(idOrderDetail.asc());
    List<OrderDetail> results = query.list(orderDetail);
    return new PageImpl<OrderDetail>(results, pageable, totalFound);
  }

  public Page<OrderDetail> findAllByCustomerOrder(CustomerOrder customerOrderField,
      GlobalSearch globalSearch, Pageable pageable) {
    // NumberPath<OrderDetailPK> idOrderDetail = new
    // NumberPath<OrderDetailPK>(OrderDetailPK.class, "id");
    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    JPQLQuery query = getQueryFrom(orderDetail);
    BooleanBuilder where = new BooleanBuilder(orderDetail.customerOrder.eq(customerOrderField));
    if (globalSearch != null) {
      String txt = globalSearch.getText();
      where.and(orderDetail.quantity.like("%".concat(txt).concat("%")));
    }
    query.where(where);
    long totalFound = query.count();
    if (pageable != null) {
      if (pageable.getSort() != null) {
        for (Sort.Order order : pageable.getSort()) {
          Order direction = order.isAscending() ? Order.ASC : Order.DESC;
          switch (((order.getProperty()))) {
            case "quantity":
              query.orderBy(new OrderSpecifier<Integer>(direction, orderDetail.quantity));
              break;
          }
        }
      }
      query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }
    // query.orderBy(idOrderDetail.asc());
    List<OrderDetail> results = query.list(orderDetail);
    return new PageImpl<OrderDetail>(results, pageable, totalFound);
  }
}

package com.disid.restful.repository;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OrderDetailRepository
    extends Repository<OrderDetail, OrderDetailPK>, OrderDetailRepositoryCustom {

  long countByCustomerOrder(CustomerOrder customerOrderField);

  OrderDetail findOne(OrderDetailPK orderDetailPK);

}

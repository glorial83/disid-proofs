package com.disid.restful.repository;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface OrderDetailRepository
    extends Repository<OrderDetail, OrderDetailPK>, OrderDetailRepositoryCustom {

  long countByCustomerOrder(CustomerOrder customerOrderField);

  OrderDetail findOne(OrderDetailPK orderDetailPK);

  List<OrderDetail> findAll(Iterable<OrderDetailPK> detailPks);

  /**
   * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
   * 
   * @param entity
   * @return the saved entity
   */
  OrderDetail save(OrderDetail orderDetail);

}

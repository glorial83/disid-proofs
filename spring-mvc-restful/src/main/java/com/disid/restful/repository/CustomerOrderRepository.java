package com.disid.restful.repository;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

@RooJpaRepository(entity = CustomerOrder.class)
public interface CustomerOrderRepository {

  /**
   * Retrieves a {@link CustomerOrder} by its id, with the related 
   * {@link OrderDetail} data through a join.
   * 
   * @param id must not be {@literal null}.
   * @return the CustomerOrder with the given id or {@literal null} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  @Query("select c from CustomerOrder c left join fetch c.details left join fetch c.customer where c.id = ?1")
  CustomerOrder findOne(Long id);

  @Query("select count(d) from OrderDetail d where d.customerOrder = ?1")
  long countDetailsByCustomerOrder(CustomerOrder customerOrderField);

  @Query("select d from OrderDetail d where d.id = ?1")
  OrderDetail findOneOrderDetail(OrderDetailPK orderDetailPK);
}

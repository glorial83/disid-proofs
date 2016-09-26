package com.disid.restful.repository;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

import java.util.Set;

@RooJpaRepository(entity = CustomerOrder.class)
public interface CustomerOrderRepository {

  Set<CustomerOrder> findByIdIn(Long[] orders);

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
}

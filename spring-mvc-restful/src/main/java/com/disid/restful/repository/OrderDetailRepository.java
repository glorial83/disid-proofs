package com.disid.restful.repository;

import com.disid.restful.model.OrderDetail;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import com.disid.restful.model.OrderDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RooJpaRepository(entity = OrderDetail.class)
@Repository
@Transactional(readOnly = true)
public interface OrderDetailRepository
    extends JpaRepository<OrderDetail, OrderDetailPK>, OrderDetailRepositoryCustom {

  public abstract Long countByProductId(Long id);

  public abstract Long countByCustomerOrderId(Long id);
}

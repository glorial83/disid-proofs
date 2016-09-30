package com.disid.restful.repository;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface OrderDetailRepository
    extends JpaRepository<OrderDetail, OrderDetailPK>, OrderDetailRepositoryCustom {

  long countByCustomerOrder(CustomerOrder customerOrderField);

}

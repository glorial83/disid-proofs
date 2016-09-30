package com.disid.restful.repository;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, CustomerOrderRepositoryCustom {

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

    // TODO: comprobar si funciona al ser una entidad diferente a la del repositorio
    @Query("select count(d) from OrderDetail d where d.customerOrder = ?1")
    long countDetailsByCustomerOrder(CustomerOrder customerOrderField);

    // TODO: comprobar si funciona al ser una entidad diferente a la del repositorio
    @Query("select d from OrderDetail d where d.id = ?1")
    OrderDetail findOneOrderDetail(OrderDetailPK orderDetailPK);

    public abstract Long countByCustomerId(Long id);
}

package com.disid.restful.repository;

import com.disid.restful.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CustomerRepository
    extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

  /**
   * Retrieves a Customer by its id, with the related Address data through
   * a join.
   *
   * @param id must not be {@literal null}.
   * @return the customer with the given id or {@literal null} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  @Query("select c from Customer c left join fetch c.address where c.id = ?1")
  Customer findOne(Long id);
}

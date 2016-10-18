package com.disid.restful.service.api;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.CustomerSearchForm;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

  /**
   * Adds a list of {@link CustomerOrder} to the {@link Customer#getOrders()} attribute.
   * @param customer to add the orders to
   * @param orders to add
   * @return the updated {@link Customer}
   */
  Customer addToOrders(Customer customer, Iterable<Long> orders);

  long count();

  long countByFirstNameLastName(CustomerSearchForm formBean);

  void delete(Customer customer);

  void delete(Iterable<Long> ids);


  void delete(Long id);

  List<Customer> findAll();

  Page<Customer> findAll(GlobalSearch globalSearch, Pageable pageable);

  List<Customer> findAll(Iterable<Long> ids);

  Page<Customer> findByFirstNameLastName(CustomerSearchForm formBean, GlobalSearch search,
      Pageable pageable);

  Customer findOne(Long id);

  /**
   * Removes a list of {@link CustomerOrder} from the {@link Customer#getOrders()} attribute.
   * @param customer to remove the orders from
   * @param orders to remove
   * @return the updated {@link Customer}
   */
  Customer removeFromOrders(Customer customer, Iterable<Long> orders);

  Customer save(Customer entity);

  List<Customer> save(Iterable<Customer> entities);

}

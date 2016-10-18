package com.disid.restful.service.api;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.CustomerSearchForm;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

  void delete(Customer customer);

  /**
   * Adds a list of {@link CustomerOrder} to the {@link Customer#getOrders()} attribute.
   * @param customer to add the orders to
   * @param orders to add
   * @return the updated {@link Customer}
   */
  Customer addToOrders(Customer customer, Iterable<Long> orders);

  /**
   * Removes a list of {@link CustomerOrder} from the {@link Customer#getOrders()} attribute.
   * @param customer to remove the orders from
   * @param orders to remove
   * @return the updated {@link Customer}
   */
  Customer removeFromOrders(Customer customer, Iterable<Long> orders);

  Page<Customer> findByFirstNameLastName(CustomerSearchForm formBean, GlobalSearch search,
      Pageable pageable);

  long countByFirstNameLastName(CustomerSearchForm formBean);


  Customer save(Customer entity);

  void delete(Long id);

  List<Customer> save(Iterable<Customer> entities);

  void delete(Iterable<Long> ids);

  List<Customer> findAll();

  List<Customer> findAll(Iterable<Long> ids);

  Customer findOne(Long id);

  long count();

  Page<Customer> findAll(GlobalSearch globalSearch, Pageable pageable);

}

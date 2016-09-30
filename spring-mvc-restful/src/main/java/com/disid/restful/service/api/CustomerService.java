package com.disid.restful.service.api;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.CustomerSearchForm;
import com.disid.restful.repository.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooService;

@RooService(entity = Customer.class)
public interface CustomerService {

  void delete(Customer customer);

  /**
   * Adds a list of {@link CustomerOrder} to the {@link Customer#getOrders()} attribute.
   * @param customer to add the orders to
   * @param orders to add
   * @return the updated {@link Customer}
   */
  Customer addToOrders(Customer customer, Long... orders);

  /**
   * Removes a list of {@link CustomerOrder} from the {@link Customer#getOrders()} attribute.
   * @param customer to remove the orders from
   * @param orders to remove
   * @return the updated {@link Customer}
   */
  Customer removeFromOrders(Customer customer, Long... orders);

  Page<Customer> findByFirstNameLastName(CustomerSearchForm formBean, GlobalSearch search,
      Pageable pageable);

  long countByFirstNameLastName(CustomerSearchForm formBean);
}

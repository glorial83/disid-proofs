package com.disid.restful.service.api;

import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;

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
}

package com.disid.restful.config.jackson;

import com.disid.restful.model.Category;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.Product;
import com.disid.restful.web.category.CategoryJsonMixin;
import com.disid.restful.web.customer.CustomerJsonMixin;
import com.disid.restful.web.customerorder.CustomerOrderJsonMixin;
import com.disid.restful.web.customerorder.OrderDetailJsonMixin;
import com.disid.restful.web.product.ProductJsonMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.boot.jackson.JsonComponent;

/**
 * Module to register Jackson mixins with annotations for the application
 * domain model entities.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@JsonComponent
public class DomainModelModule extends SimpleModule {

  private static final long serialVersionUID = 8965404165272410003L;

  public DomainModelModule() {
    setMixInAnnotation(Category.class, CategoryJsonMixin.class);
    setMixInAnnotation(Product.class, ProductJsonMixin.class);
    setMixInAnnotation(Customer.class, CustomerJsonMixin.class);
    setMixInAnnotation(CustomerOrder.class, CustomerOrderJsonMixin.class);
    setMixInAnnotation(OrderDetail.class, OrderDetailJsonMixin.class);
  }

}

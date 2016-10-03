package com.disid.restful.config.jackson;

import com.disid.restful.model.Category;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.Product;
import com.disid.restful.web.ProductDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Date;
import java.util.Set;

public class DomainModelModule extends SimpleModule {

  private static final long serialVersionUID = 8965404165272410003L;

  public DomainModelModule() {

    setMixInAnnotation(Category.class, CategoryMixin.class);
    setMixInAnnotation(Product.class, ProductMixin.class);
    setMixInAnnotation(Customer.class, CustomerMixin.class);
    setMixInAnnotation(CustomerOrder.class, CustomerOrderMixin.class);
    setMixInAnnotation(OrderDetail.class, OrderDetailMixin.class);
  }

  static abstract class CategoryMixin {

    @JsonIgnore
    private Set<Product> products;
  }

  static abstract class ProductMixin {

    @JsonIgnore
    private Set<Category> categories;
  }

  static abstract class CustomerMixin {

    @JsonIgnore
    private Set<CustomerOrder> orders;
  }

  static abstract class CustomerOrderMixin {

    // Not needed, creation or modification of entities will be supported
    // only with direct properties or composition. Aggregation relationships
    // will be handled through its own relation controller.
    //  @JsonDeserialize(using = CustomerDeserializer.class)
    // Not needed, just use default serialization with the @JsonIdentityInfo
    // annotation to avoid infinite cycles.
    //      @JsonSerialize(using = CustomerSerializer.class)
    // private Customer customer;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date orderDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date shippedDate;
  }

  static abstract class OrderDetailMixin {

    // The product property in the OrderDetail entity cannot be null,
    // so the product must be sent as an Id.
    @JsonDeserialize(using = ProductDeserializer.class)
    private Product product;
  }

}

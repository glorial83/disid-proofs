package com.disid.restful.config.jackson;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.Product;
import com.disid.restful.web.ProductDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Date;

public class ModelModule extends SimpleModule {

  private static final long serialVersionUID = 8965404165272410003L;

  public ModelModule() {

    //			setMixInAnnotation(Customer.class, CustomerMixin.class);
    setMixInAnnotation(CustomerOrder.class, ModelModule.CustomerOrderMixin.class);
    setMixInAnnotation(OrderDetail.class, ModelModule.OrderDetailMixin.class);
    //			setMixInAnnotation(Category.class, CategoryMixin.class);
    //            setMixInAnnotation(Product.class, ProductMixin.class);
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

  //		@JsonAutoDetect(isGetterVisibility = JsonAutoDetect.Visibility.NONE)
  //		static abstract class OrderMixin {
  //
  //			@JsonCreator
  //			public OrderMixin(Collection<LineItem> lineItems, Location location) {}
  //		}
  //
  //		static abstract class LineItemMixin {
  //
  //			@JsonCreator
  //			public LineItemMixin(String name, int quantity, Milk milk, Size size, MonetaryAmount price) {}
  //		}
  //
  //		@JsonAutoDetect(isGetterVisibility = JsonAutoDetect.Visibility.NONE)
  //		static abstract class CreditCardMixin {
  //
  //			abstract @JsonUnwrapped CreditCardNumber getNumber();
  //		}
  //
  //		@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  //		static abstract class CreditCardNumberMixin {}
}
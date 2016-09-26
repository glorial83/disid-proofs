/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.disid.restful.config;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.Product;
import com.disid.restful.web.ProductDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * Configures custom serialization and deserialization of entities.
 *
 * @author Cèsar Ordiñana
 */
@Configuration
class JacksonConfiguration {

  //  @Autowired
  //  private CustomerService customerService;
  //
  //  @Autowired
  //  private ConversionService conversionService;

  @Bean
  public Module modelModule() {
    return new ModelModule();
  }

  //  @Bean
  //  public CustomerDeserializer customerDeserializer() {
  //    return new CustomerDeserializer(new CustomerFormatter(customerService, conversionService));
  //  }

  @SuppressWarnings("serial")
  static class ModelModule extends SimpleModule {

    public ModelModule() {

      //			setMixInAnnotation(Customer.class, CustomerMixin.class);
      setMixInAnnotation(CustomerOrder.class, CustomerOrderMixin.class);
      setMixInAnnotation(OrderDetail.class, OrderDetailMixin.class);
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
}

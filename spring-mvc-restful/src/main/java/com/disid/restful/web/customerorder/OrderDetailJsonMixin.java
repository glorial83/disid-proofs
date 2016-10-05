/*
 * Copyright 2016 the original author or authors.
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
package com.disid.restful.web.customerorder;

import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.Product;
import com.disid.restful.web.ProductDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Mixin class to hold Jackson annotations for the {@link OrderDetail} entity.
 * 
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
public abstract class OrderDetailJsonMixin {

  // The product property in the OrderDetail entity cannot be null,
  // so the product must be sent as an Id, which will be serialized
  // to the Product entity
  @JsonDeserialize(using = ProductDeserializer.class)
  private Product product;

}

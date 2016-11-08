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
package com.disid.restful.web.product;

import com.disid.restful.model.Product;

import io.springlets.data.web.select2.Select2Data;

import org.springframework.data.domain.Page;

/**
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
public class Select2ProductData extends Select2Data<Product> {

  public Select2ProductData(Page<Product> page, String idExpression, String textExpression) {
    super(page, idExpression, textExpression);
  }

  @Override
  protected io.springlets.data.web.select2.Select2Data.Data createData(Product element, String id,
      String text) {
    return new ProductData(id, text, element.getDescription());
  }

  static class ProductData extends Select2Data.Data {

    private final String description;

    public ProductData(String id, String text, String description) {
      super(id, text);
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }
}

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
package com.disid.restful.web.category.html;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;

import io.springlets.web.mvc.support.MethodLinkFactorySupport;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;

import java.util.Map;

/**
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@Component
public class CategoriesCollectionThymeleafLinkFactory
    extends MethodLinkFactorySupport<CategoriesCollectionThymeleafController> {

  private static final String LIST = "list";
  private static final String DATATABLES = "datatables";
  private static final String CREATE = "create";
  private static final String CREATE_FORM = "createForm";

  public CategoriesCollectionThymeleafLinkFactory() {
    super(CategoriesCollectionThymeleafController.class);
  }

  @Override
  public UriComponents toUri(String methodName, Object[] parameters,
      Map<String, Object> pathVariables) {

    switch (methodName) {
      case LIST:
        return fromMethodCall(onController().list(null)).build();
      case DATATABLES:
        return fromMethodCall(onController().datatables(null, null, null)).replaceQuery(null)
            .build();
      case CREATE:
        return fromMethodCall(onController().create(null, null, null)).build();
      case CREATE_FORM:
        return fromMethodCall(onController().createForm(null)).build();
      default:
        throw new IllegalArgumentException("Invalid method name: " + methodName);
    }
  }
}

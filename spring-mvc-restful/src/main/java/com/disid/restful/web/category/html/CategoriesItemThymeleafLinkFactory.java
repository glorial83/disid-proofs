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
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;

import java.util.Map;

/**
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@Component
public class CategoriesItemThymeleafLinkFactory
    extends MethodLinkFactorySupport<CategoriesItemThymeleafController> {

  private static final String SHOW = "show";
  private static final String EDIT_FORM = "editForm";
  private static final String UPDATE = "update";
  private static final String DELETE = "delete";

  public CategoriesItemThymeleafLinkFactory() {
    super(CategoriesItemThymeleafController.class);
  }

  @Override
  public UriComponents toUri(String methodName, Object[] parameters,
      Map<String, Object> pathVariables) {

    Assert.notEmpty(pathVariables, "CategoriesItemThymeleafController links need at least "
        + "the Category id Path Variable with the 'category' key");

    Assert.notNull(pathVariables.get("category"),
        "CategoriesItemThymeleafController links need at least "
            + "the Category id Path Variable with the 'category' key");

    switch (methodName) {
      case SHOW:
        return fromMethodCall(onController().show(null, null)).buildAndExpand(pathVariables);
      case EDIT_FORM:
        return fromMethodCall(onController().editForm(null, null)).buildAndExpand(pathVariables);
      case UPDATE:
        return fromMethodCall(onController().update(null, null, null))
            .buildAndExpand(pathVariables);
      case DELETE:
        return fromMethodCall(onController().delete(null)).buildAndExpand(pathVariables);
      default:
        throw new IllegalArgumentException("Invalid method name: " + methodName);
    }
  }
}

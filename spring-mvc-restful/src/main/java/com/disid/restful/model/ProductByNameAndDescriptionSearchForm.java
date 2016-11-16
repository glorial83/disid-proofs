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
package com.disid.restful.model;

import org.springframework.util.StringUtils;

/**
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 *
 */
public class ProductByNameAndDescriptionSearchForm {

  private String name;

  private String description;

  public ProductByNameAndDescriptionSearchForm() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param name
   * @param description
   */
  public ProductByNameAndDescriptionSearchForm(String name, String description) {
    super();
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isEmpty() {
    return !StringUtils.hasText(name) && !StringUtils.hasText(description);
  }
}

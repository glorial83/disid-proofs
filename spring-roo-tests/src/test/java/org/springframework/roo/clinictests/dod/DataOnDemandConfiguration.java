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
package org.springframework.roo.clinictests.dod;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Configuration for the data on demand classes.
 * To use it in your tests if your are using Spring Boot test slice 
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@TestConfiguration
public class DataOnDemandConfiguration {

  private final TestEntityManager entityManager;

  public DataOnDemandConfiguration(TestEntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Bean
  public PetDataOnDemand petDataOnDemand() {
    return new PetDataOnDemand(entityManager);
  }

  @Bean
  public OwnerDataOnDemand ownerDataOnDemand() {
    return new OwnerDataOnDemand(entityManager);
  }

}

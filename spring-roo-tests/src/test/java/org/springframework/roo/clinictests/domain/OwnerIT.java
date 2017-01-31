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
package org.springframework.roo.clinictests.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.roo.clinictests.dod.DataOnDemandConfiguration;
import org.springframework.roo.clinictests.dod.OwnerDataOnDemand;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for the {@link Owner} entity.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(DataOnDemandConfiguration.class)
public class OwnerIT {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private OwnerDataOnDemand dod;

  @Test
  public void saveShouldPersistData() throws Exception {
    // Setup
    Owner newRandomOwner = dod.getNewRandomTransientOwner();

    // Exercise
    Owner owner = entityManager.persistFlushFind(newRandomOwner);

    // Verify
    assertThat(owner.getFirstName()).as("Check owner first name")
        .isEqualTo(newRandomOwner.getFirstName());
    assertThat(owner.getLastName()).as("Check owner last name")
        .isEqualTo(newRandomOwner.getLastName());
    assertThat(owner.getAddress()).as("Check owner address").isEqualTo(newRandomOwner.getAddress());
    assertThat(owner.getBirthDay()).as("Check owner birthday")
        .isCloseTo(newRandomOwner.getBirthDay(), 0);
    assertThat(owner.getCity()).as("Check owner city").isEqualTo(newRandomOwner.getCity());
    assertThat(owner.getEmail()).as("Check owner email").isEqualTo(newRandomOwner.getEmail());
    assertThat(owner.getHomePage()).as("Check owner home page")
        .isEqualTo(newRandomOwner.getHomePage());
    assertThat(owner.getTelephone()).as("Check owner telephone")
        .isEqualTo(newRandomOwner.getTelephone());
  }
}

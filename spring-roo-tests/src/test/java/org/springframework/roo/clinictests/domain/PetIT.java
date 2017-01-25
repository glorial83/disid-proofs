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
import org.springframework.context.annotation.Import;
import org.springframework.roo.clinictests.dod.DataOnDemandConfiguration;
import org.springframework.roo.clinictests.dod.PetDataOnDemand;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for the {@link Pet} entity.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(DataOnDemandConfiguration.class)
public class PetIT {

  @Autowired
  private PetDataOnDemand dod;

  @Test
  public void saveShouldPersistData() throws Exception {
    // Setup
    Pet newRandomPet = dod.getNewRandomTransientPet();

    // Exercise
    Pet pet = dod.getEntityManager().persistFlushFind(newRandomPet);

    // Verify
    assertThat(pet.getName()).as("Check pet name").isEqualTo(newRandomPet.getName());
    assertThat(pet.getWeight()).as("Check pet weight").isEqualTo(newRandomPet.getWeight());
    assertThat(pet.getType()).as("Check pet type").isEqualTo(newRandomPet.getType());
    assertThat(pet.isSendReminders()).as("Check pet sendReminders")
        .isEqualTo(newRandomPet.isSendReminders());
  }
}

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
import org.springframework.roo.clinictests.dod.OwnerFactory;
import org.springframework.roo.clinictests.dod.PetFactory;

import java.util.Arrays;
import java.util.Collections;

/**
 * Unit tests for the {@link Owner} entity.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
public class OwnerTest {

  private final OwnerFactory ownerFactory = new OwnerFactory();

  private final PetFactory petFactory = new PetFactory();

  @Test
  public void addToVisitsShouldAddTheVisitToTheVisitsRelationship() throws Exception {
    // Setup
    Owner owner = ownerFactory.create(0);
    Pet pet1 = petFactory.create(0);
    Pet pet2 = petFactory.create(1);

    // Exercise
    owner.addToPets(Arrays.asList(pet1, pet2));

    // Verify
    assertThat(owner.getPets()).as("Check 'addToPets' adds the pets to the relationship")
        .contains(pet1).contains(pet2);
    assertThat(owner).as("Check 'addToPets' updates the pet relationship side")
        .isEqualTo(pet1.getOwner()).isEqualTo(pet2.getOwner());
  }

  @Test
  public void removeFromVisitsShouldRemoveTheVisitFromTheVisitsRelationship() throws Exception {
    // Setup
    Owner owner = ownerFactory.create(0);
    Pet pet1 = petFactory.create(0);
    Pet pet2 = petFactory.create(1);
    owner.addToPets(Arrays.asList(pet1, pet2));

    // Exercise
    owner.removeFromPets(Collections.singleton(pet1));

    // Verify
    assertThat(pet1.getOwner()).as("Check 'removeFromPets' updates the pet relationship side")
        .isNull();
    assertThat(owner.getPets()).as("Check 'removeFromPets' removes a pet from the relationship")
        .doesNotContain(pet1).contains(pet2);
  }

}

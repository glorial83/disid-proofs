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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.roo.clinictests.dod.PetFactory;
import org.springframework.roo.clinictests.dod.VisitFactory;
import org.springframework.roo.clinictests.domain.reference.PetType;

import java.util.Arrays;
import java.util.Collections;

/**
 * Unit tests for the {@link Pet} entity.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
public class PetTest {

  private static final float WEIGHT = 10.0f;

  private static final String NAME = "Duke";

  private final PetFactory petFactory = new PetFactory();

  private final VisitFactory visitFactory = new VisitFactory();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void createWhenNameIsNullShouldThrowException() throws Exception {
    // Setup    
    this.thrown.expect(IllegalArgumentException.class);
    // Exercise
    new Pet(null, WEIGHT, PetType.DOG, false);
  }

  @Test
  public void createWhenNameIsEmptyShouldThrowException() throws Exception {
    // Setup    
    this.thrown.expect(IllegalArgumentException.class);
    // Exercise
    new Pet("", WEIGHT, PetType.DOG, false);
  }

  @Test
  public void createWhenWeightIsNullShouldThrowException() throws Exception {
    // Setup    
    this.thrown.expect(IllegalArgumentException.class);
    // Exercise
    new Pet(NAME, null, PetType.DOG, false);
  }

  @Test
  public void createWhenWeightIsLessThanZeroShouldThrowException() throws Exception {
    // Setup    
    this.thrown.expect(IllegalArgumentException.class);
    // Exercise
    new Pet(NAME, -1.0f, PetType.DOG, false);
  }

  @Test
  public void createWhenPetTypeIsNullShouldThrowException() throws Exception {
    // Setup    
    this.thrown.expect(IllegalArgumentException.class);
    // Exercise
    new Pet(NAME, WEIGHT, null, false);
  }

  @Test
  public void addToVisitsShouldAddTheVisitToTheVisitsRelationship() throws Exception {
    // Setup
    Pet pet = petFactory.create(0);
    Visit visit1 = visitFactory.create(0);
    Visit visit2 = visitFactory.create(1);

    // Exercise
    pet.addToVisits(Arrays.asList(visit1, visit2));

    // Verify
    assertThat(pet.getVisits()).as("Check 'addToVisits' adds the visits to the relationship")
        .contains(visit1).contains(visit2);
    assertThat(pet).as("Check 'addToVisits' updates the visit relationship side")
        .isEqualTo(visit1.getPet()).isEqualTo(visit2.getPet());
  }

  @Test
  public void removeFromVisitsShouldRemoveTheVisitFromTheVisitsRelationship() throws Exception {
    // Setup
    Pet pet = petFactory.create(0);
    Visit visit1 = visitFactory.create(0);
    Visit visit2 = visitFactory.create(1);
    pet.addToVisits(Arrays.asList(visit1, visit2));

    // Exercise
    pet.removeFromVisits(Collections.singleton(visit1));

    // Verify
    assertThat(visit1.getPet()).as("Check 'removeFromVisits' updates the visit relationship side")
        .isNull();
    assertThat(pet.getVisits()).as("Check 'removeFromVisits' removes a visit from the relationship")
        .doesNotContain(visit1).contains(visit2);
  }

}

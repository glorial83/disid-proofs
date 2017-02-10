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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.roo.clinictests.config.SpringDataJpaDetachableRepositoryConfiguration;
import org.springframework.roo.clinictests.dod.DataOnDemandConfiguration;
import org.springframework.roo.clinictests.dod.PetDataOnDemand;
import org.springframework.roo.clinictests.dod.PetFactory;
import org.springframework.roo.clinictests.dod.VisitFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Integration tests for the {@link Pet} entity.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Import({DataOnDemandConfiguration.class, SpringDataJpaDetachableRepositoryConfiguration.class})
public class PetIT {

  @Autowired
  private PetDataOnDemand dod;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void saveShouldPersistData() throws Exception {
    // Setup
    Pet newRandomPet = dod.getNewRandomTransientPet();

    // Exercise
    Pet pet = entityManager.persistFlushFind(newRandomPet);

    // Verify
    assertThat(pet.getName()).as("Check pet name").isEqualTo(newRandomPet.getName());
    assertThat(pet.getWeight()).as("Check pet weight").isEqualTo(newRandomPet.getWeight());
    assertThat(pet.getType()).as("Check pet type").isEqualTo(newRandomPet.getType());
    assertThat(pet.isSendReminders()).as("Check pet sendReminders")
        .isEqualTo(newRandomPet.isSendReminders());
  }

  @Test
  public void testEqualityConstraints() {
    Pet entity = dod.getNewRandomTransientPet();

    Set<Pet> tuples = new HashSet<Pet>();

    assertFalse(tuples.contains(entity));
    tuples.add(entity);
    assertTrue(tuples.contains(entity));

    Pet persisted = entityManager.persist(entity);
    entityManager.flush();

    assertTrue("The entity is NOT found after it's persisted", tuples.contains(persisted));

    //The entity is found after the entity is detached
    assertTrue(tuples.contains(entity));

    Pet merged = entityManager.merge(entity);
    assertTrue("The entity is NOT found after it's merged", tuples.contains(merged));

    Pet found = entityManager.find(Pet.class, entity.getId());
    assertTrue("The entity is NOT found after it's loaded " + "in an other Persistence Context",
        tuples.contains(found));

    Pet reference = entityManager.getEntityManager().getReference(Pet.class, entity.getId());
    assertTrue(
        "The entity is NOT found after it's loaded as a Proxy " + "in an other Persistence Context",
        tuples.contains(reference));
  }

  @Test
  public void removeFromVisitsShouldRemoveTheVisitFromTheVisitsRelationship() throws Exception {
    // Setup
    PetFactory petFactory = new PetFactory();
    VisitFactory visitFactory = new VisitFactory();
    Pet pet = petFactory.create(0);
    Visit visit1 = entityManager.persistFlushFind(visitFactory.create(0));
    Visit visit2 = entityManager.persistFlushFind(visitFactory.create(1));
    pet.addToVisits(Arrays.asList(visit1, visit2));
    pet = entityManager.persistFlushFind(pet);

    // Exercise
    pet.removeFromVisits(Collections.singleton(visit1));
    pet = entityManager.persistFlushFind(pet);

    // Verify
    assertThat(visit1.getPet()).as("Check 'removeFromVisits' updates the visit relationship side")
        .isNull();
    assertThat(pet.getVisits()).as("Check 'removeFromVisits' removes a visit from the relationship")
        .doesNotContain(visit1).contains(visit2);
  }

}

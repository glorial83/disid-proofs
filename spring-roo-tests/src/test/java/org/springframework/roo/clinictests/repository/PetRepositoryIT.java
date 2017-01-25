package org.springframework.roo.clinictests.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.springlets.data.domain.GlobalSearch;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.roo.clinictests.dod.DataOnDemandConfiguration;
import org.springframework.roo.clinictests.dod.PetDataOnDemand;
import org.springframework.roo.clinictests.domain.Pet;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Integration tests for the {@link PetRepository}.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(DataOnDemandConfiguration.class)
public class PetRepositoryIT {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  private PetRepository repository;

  @Autowired
  private PetDataOnDemand dod;

  @Before
  public void checkDataOnDemandHasInitializedCorrectly() {
    assertThat(dod.getRandomPet())
        .as("Check data on demand for 'Pet' initializes correctly by getting a random Pet")
        .isNotNull();
  }

  @Test
  public void countShouldReturnExpectedValue() {
    // Verify
    assertThat(repository.count()).as("Check there are available 'Pet' entries").isGreaterThan(0);
  }

  @Test
  public void findOneShouldReturnExistingPet() {
    // Setup
    Long id = getRandomPetId();
    // Exercise
    Pet pet = repository.findOne(id);
    // Verify
    assertThat(pet).as("Check that findOne illegally returned null for id %s", id).isNotNull();
    assertThat(id).as("Check the identifier of the found 'Pet' is the same used to look for it")
        .isEqualTo(pet.getId());
  }

  @Test
  public void findAllShouldReturnAllPets() {
    // Setup
    assertThat(repository.count())
        .as("Check the number of entries is not too big (250 entries). "
            + "If it is, please review the tests so it doesn't take too long to run them")
        .isLessThan(250);
    // Exercise    
    List<Pet> result = repository.findAll();
    // Verify
    assertThat(result).as("Check 'findAll' returns a not null list of entries").isNotNull();
    assertThat(result.size()).as("Check 'findAll' returns a not empty list of entries")
        .isGreaterThan(0);
  }

  @Test
  public void saveShouldIncrementVersion() {
    // Setup
    Long id = getRandomPetId();
    Pet pet = repository.findOne(id);
    pet.setName(pet.getName() + " modified");
    Integer currentVersion = pet.getVersion();

    // Exercise
    Pet merged = repository.saveAndFlush(pet);

    // Verify
    assertThat(merged.getId()).as("Check the updated Pet %s still has the original id")
        .isEqualTo(id);
    assertThat(currentVersion)
        .as("Check the updated Pet %s has a version and it has been incremented").isNotNull()
        .isLessThan(pet.getVersion());
  }

  @Test
  public void persistShouldGenerateIdValue() {
    // Setup
    // Exercise
    Pet pet = dod.getNewRandomTransientPet();
    // Verify
    assertThat(pet).as("Check the Data on demand generated a new non null 'Pet'").isNotNull();
    assertThat(pet.getId()).as("Check the Data on demand generated a new 'Pet' whose id is null")
        .isNull();
    try {
      pet = repository.saveAndFlush(pet);
    } catch (final ConstraintViolationException e) {
      final StringBuilder msg = new StringBuilder();
      for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter
          .hasNext();) {
        final ConstraintViolation<?> cv = iter.next();
        msg.append("[").append(cv.getRootBean().getClass().getName()).append(".")
            .append(cv.getPropertyPath()).append(": ").append(cv.getMessage())
            .append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
      }
      throw new IllegalStateException(msg.toString(), e);
    }
    assertThat(pet.getId()).as("Check a 'Pet' (%s) id is not null after been persisted", pet)
        .isNotNull();
  }

  @Test
  public void deleteShouldMakePetUnavailable() {
    // Setup
    Long id = getRandomPetId();
    Pet pet = repository.findOne(id);
    // Exercise
    repository.delete(pet);
    // Verify
    assertThat(repository.findOne(id))
        .as("Check the deleted 'Pet' %s is no longer available with 'findOne'", pet).isNull();
  }

  @Test
  public void findAllCustomNotFilteredNotPagedShouldReturnAllPets() {
    // Exercise
    Page<Pet> all = repository.findAll((GlobalSearch) null, new PageRequest(0, dod.getSize()));

    // Verify
    assertThat(all.getNumberOfElements())
        .as("Check 'findAll' with null 'GlobalSearch' and no pagination returns all entries")
        .isEqualTo(dod.getSize());
  }

  @Test
  public void findAllCustomFilteredNotPagedShouldReturnMatchingPets() {
    // Setup
    Pet pet = dod.getRandomPet();
    GlobalSearch search = new GlobalSearch(pet.getName());

    // Exercise
    Page<Pet> all = repository.findAll(search, new PageRequest(0, dod.getSize()));

    // Verify
    assertThat(all.getNumberOfElements())
        .as("Check 'findAll' with 'GlobalSearch' of '%s' returns the single "
            + "'Pet' whose name is equal to the filter", pet.getName())
        .isEqualTo(1);

    assertThat(all.getContent().get(0).getName())
        .as("Check 'findAll' with 'GlobalSearch' of '%s' returns a pet whose name is equal "
            + "to the filter 'Pet' whose name is equal to the filter", pet.getName())
        .isEqualTo(pet.getName());
  }

  @Test
  public void findAllCustomNotFilteredPagedShouldReturnAPetsPage() {

    // Exercise
    Page<Pet> all = repository.findAll((GlobalSearch) null, new PageRequest(0, 3));

    // Verify
    assertThat(all.getNumberOfElements())
        .as("Check result number is not greater than the page size").isLessThanOrEqualTo(3);
  }

  private Long getRandomPetId() {
    Pet pet = dod.getRandomPet();
    Long id = pet.getId();
    assertThat(id).as("Check the Data on demand generated a 'Pet' with an identifier").isNotNull();
    return id;
  }

}

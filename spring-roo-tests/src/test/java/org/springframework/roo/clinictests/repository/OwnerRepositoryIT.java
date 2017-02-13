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
import org.springframework.roo.clinictests.config.SpringDataJpaDetachableRepositoryConfiguration;
import org.springframework.roo.clinictests.dod.DataOnDemandConfiguration;
import org.springframework.roo.clinictests.dod.OwnerDataOnDemand;
import org.springframework.roo.clinictests.domain.Owner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Integration tests for the {@link OwnerRepository}.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Import({DataOnDemandConfiguration.class, SpringDataJpaDetachableRepositoryConfiguration.class})
public class OwnerRepositoryIT {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  private OwnerRepository repository;

  @Autowired
  private OwnerDataOnDemand dod;

  @Before
  public void checkDataOnDemandHasInitializedCorrectly() {
    assertThat(dod.getRandomOwner())
        .as("Check data on demand for 'Owner' initializes correctly by getting a random Owner")
        .isNotNull();
  }

  @Test
  public void countShouldReturnExpectedValue() {
    // Verify
    assertThat(repository.count()).as("Check there are available 'Owner' entries").isGreaterThan(0);
  }

  @Test
  public void findOneShouldReturnExistingOwner() {
    // Setup
    Long id = getRandomOwnerId();
    // Exercise
    Owner owner = repository.findOne(id);
    // Verify
    assertThat(owner).as("Check that findOne illegally returned null for id %s", id).isNotNull();
    assertThat(id).as("Check the identifier of the found 'Owner' is the same used to look for it")
        .isEqualTo(owner.getId());
  }

  @Test
  public void findAllShouldReturnAllOwners() {
    // Setup
    assertThat(repository.count())
        .as("Check the number of entries is not too big (250 entries). "
            + "If it is, please review the tests so it doesn't take too long to run them")
        .isLessThan(250);
    // Exercise    
    List<Owner> result = repository.findAll();
    // Verify
    assertThat(result).as("Check 'findAll' returns a not null list of entries").isNotNull();
    assertThat(result.size()).as("Check 'findAll' returns a not empty list of entries")
        .isGreaterThan(0);
  }

  @Test
  public void saveShouldIncrementVersion() {
    // Setup
    Long id = getRandomOwnerId();
    Owner owner = repository.findOne(id);
    owner.setFirstName(owner.getFirstName() + " modified");
    Integer currentVersion = owner.getVersion();

    // Exercise
    Owner merged = repository.saveAndFlush(owner);

    // Verify
    assertThat(merged.getId()).as("Check the updated Owner %s still has the original id")
        .isEqualTo(id);
    assertThat(currentVersion)
        .as("Check the updated Owner %s has a version and it has been incremented").isNotNull()
        .isLessThan(owner.getVersion());
  }

  @Test
  public void persistShouldGenerateIdValue() {
    // Setup
    // Exercise
    Owner owner = dod.getNewRandomTransientOwner();
    // Verify
    assertThat(owner).as("Check the Data on demand generated a new non null 'Owner'").isNotNull();
    assertThat(owner.getId())
        .as("Check the Data on demand generated a new 'Owner' whose id is null")
        .isNull();
    try {
      owner = repository.saveAndFlush(owner);
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
    assertThat(owner.getId()).as("Check a 'Owner' (%s) id is not null after been persisted", owner)
        .isNotNull();
  }

  @Test
  public void deleteShouldMakeOwnerUnavailable() {
    // Setup
    Long id = getRandomOwnerId();
    Owner owner = repository.findOne(id);
    // Exercise
    repository.delete(owner);
    // Verify
    assertThat(repository.findOne(id))
        .as("Check the deleted 'Owner' %s is no longer available with 'findOne'", owner).isNull();
  }

  @Test
  public void findAllCustomNotFilteredNotPagedShouldReturnAllOwners() {
    // Exercise
    Page<Owner> all = repository.findAll((GlobalSearch) null, new PageRequest(0, dod.getSize()));

    // Verify
    assertThat(all.getNumberOfElements())
        .as("Check 'findAll' with null 'GlobalSearch' and no pagination returns all entries")
        .isEqualTo(dod.getSize());
  }

  @Test
  public void findAllCustomFilteredNotPagedShouldReturnMatchingOwners() {
    // Setup
    Owner owner = dod.getRandomOwner();
    GlobalSearch search = new GlobalSearch(owner.getFirstName());

    // Exercise
    Page<Owner> all = repository.findAll(search, new PageRequest(0, dod.getSize()));

    // Verify
    assertThat(all.getNumberOfElements())
        .as("Check 'findAll' with 'GlobalSearch' of '%s' returns the single "
            + "'Owner' whose name is equal to the filter", owner.getFirstName())
        .isEqualTo(1);

    assertThat(all.getContent().get(0).getFirstName())
        .as("Check 'findAll' with 'GlobalSearch' of '%s' returns a owner whose name is equal "
            + "to the filter 'Owner' whose name is equal to the filter", owner.getFirstName())
        .isEqualTo(owner.getFirstName());
  }

  @Test
  public void findAllCustomNotFilteredPagedShouldReturnAOwnersPage() {

    // Exercise
    Page<Owner> all = repository.findAll((GlobalSearch) null, new PageRequest(0, 3));

    // Verify
    assertThat(all.getNumberOfElements())
        .as("Check result number is not greater than the page size").isLessThanOrEqualTo(3);
  }

  private Long getRandomOwnerId() {
    Owner owner = dod.getRandomOwner();
    Long id = owner.getId();
    assertThat(id).as("Check the Data on demand generated a 'Owner' with an identifier")
        .isNotNull();
    return id;
  }

}

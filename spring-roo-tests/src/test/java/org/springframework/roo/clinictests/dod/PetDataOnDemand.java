package org.springframework.roo.clinictests.dod;

import org.springframework.roo.clinictests.domain.Pet;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * On demand generation of {@link Pet} entities. It generates and persists by default
 * a list of entities, and allows to generate new ones. 
 * 
 * Data is generated using a simple criteria based on a given index and depending on the type 
 * of the property:
 * * String: its the property name with an index suffix.
 * * Date types: the current date.
 * * Boolean: true value.
 * * Numbers: the index value.
 * * Enum: the first enum value.
 *
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
public class PetDataOnDemand {

  /**
   * Random generator for the entities index.
   */
  private final Random rnd = new SecureRandom();

  /**
   * List of created entities.
   */
  private List<Pet> data;

  /**
   * EntityManager to persist the entities.
   */
  private final EntityManager entityManager;

  /**
   * Number of elements to create and persist.
   */
  private final int size;

  private final PetFactory factory = new PetFactory();

  /**
   * Creates a new {@link PetDataOnDemand}.
   * @param entityManager to persist entities
   */
  public PetDataOnDemand(EntityManager entityManager) {
    this(entityManager, 10);
  }

  /**
   * Creates a new {@link PetDataOnDemand}.
   * @param entityManager to persist entities
   * @param size the number of entities to create and persist initially
   */
  public PetDataOnDemand(EntityManager entityManager, int size) {
    this.entityManager = entityManager;
    this.size = size;
  }

  /**
   * Returns the {@link EntityManager} used to persist the entities.
   * @return the entity manager
   */
  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * Returns the number of entities to create and persist initially.
   * @return 
   */
  public int getSize() {
    return size;
  }

  /**
   * Returns a generated and persisted {@link Pet} in a given index.
   * 
   * @param index the position of the {@link Pet} to return
   * @return Pet the specific {@link Pet}
   */
  public Pet getSpecificPet(int index) {
    init();
    if (index < 0) {
      index = 0;
    }
    if (index > (data.size() - 1)) {
      index = data.size() - 1;
    }
    return data.get(index);
  }

  /**
   * Returns a generated and persisted {@link Pet} in a random index.
   * 
   * @return Pet a random {@link Pet}
   */
  public Pet getRandomPet() {
    init();
    return data.get(rnd.nextInt(data.size()));
  }

  /**
   * Creates a new transient Pet in a random index out of the initial list of the created entities,
   * with an index greater than {@link PetDataOnDemand#getSize()} - 1.
   * 
   * @return Pet the generated transient Pet
   */
  public Pet getNewRandomTransientPet() {
    int randomIndex = getSize() + rnd.nextInt(Integer.MAX_VALUE - getSize());
    return factory.create(randomIndex);
  }

  /**
   * Creates the initial list of generated entities.
   */
  void init() {

    int from = 0;
    int to = size;

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Pet> cq = cb.createQuery(Pet.class);
    Root<Pet> rootEntry = cq.from(Pet.class);
    CriteriaQuery<Pet> all = cq.select(rootEntry);
    TypedQuery<Pet> allQuery =
        entityManager.createQuery(all).setFirstResult(from).setMaxResults(to);
    data = allQuery.getResultList();

    if (data == null) {
      throw new IllegalStateException(
          "Find entries implementation for 'Pet' illegally returned null");
    }
    if (!data.isEmpty()) {
      return;
    }

    data = new ArrayList<Pet>();
    for (int i = from; i < to; i++) {
      Pet obj = factory.create(i);
      try {
        entityManager.persist(obj);
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
      entityManager.flush();
      data.add(obj);
    }
  }

}

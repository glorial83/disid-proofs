package org.springframework.roo.clinictests.dod;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.roo.clinictests.domain.Owner;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * On demand generation of {@link Owner} entities. It generates and persists by default
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
public class OwnerDataOnDemand {

  /**
   * Random generator for the entities index.
   */
  private final Random rnd = new SecureRandom();

  /**
   * List of created entities.
   */
  private List<Owner> data;

  /**
   * EntityManager to persist the entities.
   */
  private final TestEntityManager entityManager;

  /**
   * Number of elements to create and persist.
   */
  private final int size;

  private final OwnerFactory factory = new OwnerFactory();

  /**
   * Creates a new {@link OwnerDataOnDemand}.
   * @param entityManager to persist entities
   */
  public OwnerDataOnDemand(TestEntityManager entityManager) {
    this(entityManager, 10);
  }

  /**
   * Creates a new {@link OwnerDataOnDemand}.
   * @param entityManager to persist entities
   * @param size the number of entities to create and persist initially
   */
  public OwnerDataOnDemand(TestEntityManager entityManager, int size) {
    this.entityManager = entityManager;
    this.size = size;
  }

  /**
   * Returns the {@link TestEntityManager} used to persist the entities.
   * @return the entity manager
   */
  public TestEntityManager getEntityManager() {
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
   * Returns a generated and persisted {@link Owner} in a given index.
   * 
   * @param index the position of the {@link Owner} to return
   * @return Owner the specific {@link Owner}
   */
  public Owner getSpecificOwner(int index) {
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
   * Returns a generated and persisted {@link Owner} in a random index.
   * 
   * @return Owner a random {@link Owner}
   */
  public Owner getRandomOwner() {
    init();
    return data.get(rnd.nextInt(data.size()));
  }

  /**
   * Creates a new transient Owner in a random index out of the initial list of the created entities,
   * with an index greater than {@link OwnerDataOnDemand#getSize()} - 1.
   * 
   * @return Owner the generated transient Owner
   */
  public Owner getNewRandomTransientOwner() {
    int randomIndex = getSize() + rnd.nextInt(Integer.MAX_VALUE - getSize());
    return factory.create(randomIndex);
  }

  /**
   * Creates the initial list of generated entities.
   */
  void init() {

    int from = 0;
    int to = size;

    CriteriaBuilder cb = entityManager.getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Owner> cq = cb.createQuery(Owner.class);
    Root<Owner> rootEntry = cq.from(Owner.class);
    CriteriaQuery<Owner> all = cq.select(rootEntry);
    TypedQuery<Owner> allQuery =
        entityManager.getEntityManager().createQuery(all).setFirstResult(from).setMaxResults(to);
    data = allQuery.getResultList();

    if (data == null) {
      throw new IllegalStateException(
          "Find entries implementation for 'Owner' illegally returned null");
    }
    if (!data.isEmpty()) {
      return;
    }

    data = new ArrayList<Owner>();
    for (int i = from; i < to; i++) {
      Owner obj = factory.create(i);
      try {
        obj = entityManager.persist(obj);
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

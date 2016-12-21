package org.springframework.roo.petclinic.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.petclinic.domain.Visit;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooFinder;

/**
 * = VisitRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Visit.class, finders = { @RooFinder("findByDescriptionAndVisitDate"), @RooFinder("findByVisitDateBetween"), @RooFinder("findByDescriptionLike") })
public interface VisitRepository {
}

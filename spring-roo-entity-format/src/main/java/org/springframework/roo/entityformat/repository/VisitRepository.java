package org.springframework.roo.entityformat.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.entityformat.domain.Visit;
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

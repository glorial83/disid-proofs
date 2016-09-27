package com.springsource.petclinic.repository;
import com.springsource.petclinic.domain.Visit;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.addon.finder.annotations.RooFinder;
import org.springframework.roo.addon.finder.annotations.RooFinders;

@RooJpaRepository(entity = Visit.class)
@RooFinders(finders = { @RooFinder(finder = "findByDescriptionAndVisitDate", returnType = Visit.class), @RooFinder(finder = "findByVisitDateBetween", returnType = Visit.class), @RooFinder(finder = "findByDescriptionLike", returnType = Visit.class) })
public interface VisitRepository {
}

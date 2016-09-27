package com.springsource.petclinic.repository;
import com.springsource.petclinic.domain.Pet;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.addon.finder.annotations.RooFinder;
import org.springframework.roo.addon.finder.annotations.RooFinders;

@RooJpaRepository(entity = Pet.class)
@RooFinders(finders = { @RooFinder(finder = "findByNameAndWeight", returnType = Pet.class), @RooFinder(finder = "findByOwner", returnType = Pet.class), @RooFinder(finder = "findBySendRemindersAndWeightLessThan", returnType = Pet.class), @RooFinder(finder = "findByTypeAndNameLike", returnType = Pet.class) })
public interface PetRepository {
}

package org.springframework.roo.petclinic.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooFinder;
import org.springframework.roo.petclinic.domain.PetNameAndWeightFormBean;

/**
 * = PetRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Pet.class, finders = { @RooFinder(value = "findByNameAndWeight", formBean = PetNameAndWeightFormBean.class), @RooFinder("findByOwner"), @RooFinder("findBySendRemindersAndWeightLessThan"), @RooFinder("findByTypeAndNameLike") })
public interface PetRepository {
}

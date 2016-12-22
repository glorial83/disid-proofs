package org.springframework.roo.multiselect.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.multiselect.domain.Pet;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooFinder;
import org.springframework.roo.multiselect.domain.PetNameAndWeightFormBean;

/**
 * = PetRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Pet.class, finders = { @RooFinder(value = "findByNameAndWeight", formBean = PetNameAndWeightFormBean.class), @RooFinder("findByOwner"), @RooFinder("findBySendRemindersAndWeightLessThan"), @RooFinder("findByTypeAndNameLike") })
public interface PetRepository {
}

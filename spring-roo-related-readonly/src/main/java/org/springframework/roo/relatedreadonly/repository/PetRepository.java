package org.springframework.roo.relatedreadonly.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.relatedreadonly.domain.Pet;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooFinder;
import org.springframework.roo.relatedreadonly.domain.PetNameAndWeightFormBean;

/**
 * = PetRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Pet.class, finders = { @RooFinder(value = "findByNameAndWeight", returnType = Pet.class, formBean = PetNameAndWeightFormBean.class), @RooFinder(value = "findByOwner", returnType = Pet.class), @RooFinder(value = "findBySendRemindersAndWeightLessThan", returnType = Pet.class), @RooFinder(value = "findByTypeAndNameLike", returnType = Pet.class) })
public interface PetRepository {
}

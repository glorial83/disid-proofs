package org.springframework.roo.petclinic.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import org.springframework.roo.petclinic.domain.Owner;
import org.springframework.roo.petclinic.domain.Pet;

import io.springlets.data.domain.GlobalSearch;

/**
 * = PetRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = Pet.class)
public interface PetRepositoryCustom {
  
  /**
   * Returns all the pets that are not assigned yet to the provided Owner
   * 
   * @param globalSearch
   * @param pageable
   * @return Page
   */
  public Page<Pet> findAllNotAssignedToOwner(Owner owner, GlobalSearch globalSearch,
      Pageable pageable);
}

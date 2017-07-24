package org.springframework.roo.petclinic.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import org.springframework.roo.petclinic.domain.Owner;
import org.springframework.roo.petclinic.domain.Pet;

import io.springlets.data.domain.GlobalSearch;

/**
 * = PetService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Pet.class)
public interface PetService {

  /**
   * Returns all the pets that are not assigned yet to the provided Owner
   * 
   * @param globalSearch
   * @param pageable
   * @return Page
   */
  public abstract Page<Pet> findAllNotAssignedToOwner(Owner owner, GlobalSearch globalSearch,
      Pageable pageable);
}

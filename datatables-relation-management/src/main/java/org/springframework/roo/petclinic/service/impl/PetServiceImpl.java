package org.springframework.roo.petclinic.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.petclinic.domain.Owner;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.roo.petclinic.service.api.PetService;

import io.springlets.data.domain.GlobalSearch;

/**
 * = PetServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = PetService.class)
public class PetServiceImpl implements PetService {

  /**
   * {@inheritDoc}
   */
  public Page<Pet> findAllNotAssignedToOwner(Owner owner, GlobalSearch globalSearch,
      Pageable pageable) {
    return getPetRepository().findAllNotAssignedToOwner(owner, globalSearch, pageable);
  }
}

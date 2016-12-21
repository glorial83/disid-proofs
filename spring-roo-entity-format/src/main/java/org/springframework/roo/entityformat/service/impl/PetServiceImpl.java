package org.springframework.roo.entityformat.service.impl;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.entityformat.domain.Pet;
import org.springframework.roo.entityformat.service.api.PetService;

/**
 * = PetServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = PetService.class)
public class PetServiceImpl {

  @Override
  public Class<Pet> getEntityType() {
    return Pet.class;
  }

  @Override
  public Class<Long> getIdType() {
    return Long.class;
  }
}

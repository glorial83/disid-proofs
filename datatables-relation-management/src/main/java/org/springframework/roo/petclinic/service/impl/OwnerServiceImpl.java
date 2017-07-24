package org.springframework.roo.petclinic.service.impl;

import java.util.List;

import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.addon.security.annotations.RooSecurityAuthorization;
import org.springframework.roo.addon.security.annotations.RooSecurityAuthorizations;
import org.springframework.roo.petclinic.domain.Owner;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.roo.petclinic.service.api.OwnerService;
import org.springframework.transaction.annotation.Transactional;

/**
 * = OwnerServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = OwnerService.class)
@RooSecurityAuthorizations(authorizations = {
    @RooSecurityAuthorization(method = "delete", parameters = {Owner.class}, roles = "ADMIN"),
    @RooSecurityAuthorization(method = "delete", parameters = {Iterable.class}, roles = "ADMIN")})
public class OwnerServiceImpl implements OwnerService {

  /**
   * TODO Auto-generated method documentation
   * 
   * @param owner
   * @param pets
   * @return Owner
   */
  @Transactional
  public Owner setPets(Owner owner, Iterable<Long> pets) {
    List<Pet> items = getPetService().findAll(pets);
    owner.addToPets(items);
    // Force the version update of the parent side to know that the parent has changed
    // because it has new books assigned
    // TODO: Check if this is necessary
    //owner.setVersion(owner.getVersion() + 1);
    return getOwnerRepository().save(owner);
  }

}

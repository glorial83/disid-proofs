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
   * Method that set the new pets 
   * 
   * @param owner
   * @param pets
   * @return Owner
   */
  @Transactional
  public Owner setPets(Owner owner, Iterable<Long> pets) {
    List<Pet> items = getPetService().findAll(pets);
    owner.addToPets(items);
    return getOwnerRepository().save(owner);
  }

}

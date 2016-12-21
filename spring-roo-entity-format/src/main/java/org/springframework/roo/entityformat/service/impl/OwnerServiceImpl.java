package org.springframework.roo.entityformat.service.impl;

import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.addon.security.annotations.RooSecurityAuthorization;
import org.springframework.roo.addon.security.annotations.RooSecurityAuthorizations;
import org.springframework.roo.entityformat.domain.Owner;
import org.springframework.roo.entityformat.service.api.OwnerService;

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
public class OwnerServiceImpl {

  @Override
  public Class<Owner> getEntityType() {
    return Owner.class;
  }

  @Override
  public Class<Long> getIdType() {
    return Long.class;
  }
}

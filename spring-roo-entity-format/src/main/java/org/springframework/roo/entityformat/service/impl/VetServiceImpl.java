package org.springframework.roo.entityformat.service.impl;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.entityformat.domain.Vet;
import org.springframework.roo.entityformat.service.api.VetService;

/**
 * = VetServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = VetService.class)
public class VetServiceImpl {

  @Override
  public Class<Vet> getEntityType() {
    return Vet.class;
  }

  @Override
  public Class<Long> getIdType() {
    return Long.class;
  }
}

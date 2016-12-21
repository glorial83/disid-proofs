package org.springframework.roo.entityformat.service.impl;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.entityformat.domain.Visit;
import org.springframework.roo.entityformat.service.api.VisitService;

/**
 * = VisitServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = VisitService.class)
public class VisitServiceImpl {

  @Override
  public Class<Visit> getEntityType() {
    return Visit.class;
  }

  @Override
  public Class<Long> getIdType() {
    return Long.class;
  }
}

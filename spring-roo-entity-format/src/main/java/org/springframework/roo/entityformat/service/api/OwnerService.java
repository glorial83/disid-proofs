package org.springframework.roo.entityformat.service.api;

import io.springlets.format.EntityResolver;

import org.springframework.roo.addon.layers.service.annotations.RooService;
import org.springframework.roo.entityformat.domain.Owner;

/**
 * = OwnerService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Owner.class)
public interface OwnerService extends EntityResolver<Owner, Long> {
}

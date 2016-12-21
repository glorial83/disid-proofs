package org.springframework.roo.entityformat.service.api;

import io.springlets.format.EntityResolver;

import org.springframework.roo.addon.layers.service.annotations.RooService;
import org.springframework.roo.entityformat.domain.Vet;

/**
 * = VetService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Vet.class)
public interface VetService extends EntityResolver<Vet, Long> {
}

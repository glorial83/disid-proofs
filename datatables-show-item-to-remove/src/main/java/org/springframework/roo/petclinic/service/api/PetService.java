package org.springframework.roo.petclinic.service.api;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import org.springframework.roo.petclinic.domain.Pet;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.DatatablesPageable;

/**
 * = PetService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Pet.class)
public interface PetService {
	
	public Page<Pet> findAllByIdsIn(List<Long> ids, GlobalSearch search, DatatablesPageable pageable);
	
}

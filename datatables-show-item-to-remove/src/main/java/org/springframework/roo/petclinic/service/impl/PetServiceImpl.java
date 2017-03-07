package org.springframework.roo.petclinic.service.impl;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.roo.petclinic.service.api.PetService;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.DatatablesPageable;

/**
 * = PetServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = PetService.class)
public class PetServiceImpl implements PetService {
	
    public Page<Pet> findAllByIdsIn(List<Long> ids, GlobalSearch search, DatatablesPageable pageable) {
        return getPetRepository().findAllByIdsIn(ids, search, pageable);
    }
	
}

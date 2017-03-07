package org.springframework.roo.petclinic.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import org.springframework.roo.petclinic.domain.Pet;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.DatatablesPageable;

/**
 * = PetRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = Pet.class)
public interface PetRepositoryCustom {
	
	public Page<Pet> findAllByIdsIn(List<Long> ids, GlobalSearch search, DatatablesPageable pageable);
}

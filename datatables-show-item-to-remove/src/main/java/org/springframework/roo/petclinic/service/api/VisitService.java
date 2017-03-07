package org.springframework.roo.petclinic.service.api;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import org.springframework.roo.petclinic.domain.Visit;

import io.springlets.data.domain.GlobalSearch;

/**
 * = VisitService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Visit.class)
public interface VisitService {
	
	public Page<Visit> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}

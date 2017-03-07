package org.springframework.roo.petclinic.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import org.springframework.roo.petclinic.domain.Visit;

import io.springlets.data.domain.GlobalSearch;

/**
 * = VisitRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = Visit.class)
public interface VisitRepositoryCustom {
	
	public Page<Visit> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}

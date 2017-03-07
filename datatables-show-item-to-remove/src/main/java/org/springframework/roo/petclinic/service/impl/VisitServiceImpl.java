package org.springframework.roo.petclinic.service.impl;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.petclinic.domain.Visit;
import org.springframework.roo.petclinic.service.api.VisitService;

import io.springlets.data.domain.GlobalSearch;

/**
 * = VisitServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = VisitService.class)
public class VisitServiceImpl implements VisitService {
	
    public Page<Visit> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getVisitRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }
}

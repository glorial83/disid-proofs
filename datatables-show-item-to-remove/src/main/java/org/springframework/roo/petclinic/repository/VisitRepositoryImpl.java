package org.springframework.roo.petclinic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import org.springframework.roo.petclinic.domain.QVisit;
import org.springframework.roo.petclinic.domain.Visit;

import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;

/**
 * = VisitRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = VisitRepositoryCustom.class)
public class VisitRepositoryImpl extends QueryDslRepositorySupportExt<Visit> {

    /**
     * TODO Auto-generated constructor documentation
     */
    VisitRepositoryImpl() {
        super(Visit.class);
    }
    
    public Page<Visit> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        
        QVisit visit = QVisit.visit;
        
        JPQLQuery<Visit> query = from(visit).where(visit.id.in(ids));
        
        Path<?>[] paths = new Path<?>[] {visit.description,visit.visitDate,visit.pet,visit.vet,visit.createdDate,visit.createdBy,visit.modifiedDate,visit.modifiedBy};        
        applyGlobalSearch(globalSearch, query, paths);
        
        AttributeMappingBuilder mapping = buildMapper()
			.map(DESCRIPTION, visit.description)
			.map(VISIT_DATE, visit.visitDate)
			.map(PET, visit.pet)
			.map(VET, visit.vet)
			.map(CREATED_DATE, visit.createdDate)
			.map(CREATED_BY, visit.createdBy)
			.map(MODIFIED_DATE, visit.modifiedDate)
			.map(MODIFIED_BY, visit.modifiedBy);
        
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        
        return loadPage(query, pageable, visit);
    }
}
package com.springsource.petclinic.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.springsource.petclinic.domain.Visit;
import com.springsource.petclinic.domain.QVisit;
import com.mysema.query.jpa.JPQLQuery;

@RooJpaRepositoryCustomImpl(repository = VisitRepositoryCustom.class)
public class VisitRepositoryImpl extends QueryDslRepositorySupport{

    VisitRepositoryImpl() {
        super(Visit.class);
    }
    
    private JPQLQuery getQueryFrom(QVisit qEntity){
        return from(qEntity);
    }
}
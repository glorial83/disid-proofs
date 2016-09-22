package com.springsource.petclinic.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.springsource.petclinic.domain.Vet;
import com.springsource.petclinic.domain.QVet;
import com.mysema.query.jpa.JPQLQuery;

@RooJpaRepositoryCustomImpl(repository = VetRepositoryCustom.class)
public class VetRepositoryImpl extends QueryDslRepositorySupport{

    VetRepositoryImpl() {
        super(Vet.class);
    }
    
    private JPQLQuery getQueryFrom(QVet qEntity){
        return from(qEntity);
    }
}
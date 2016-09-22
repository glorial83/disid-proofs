package com.springsource.petclinic.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.springsource.petclinic.domain.Pet;
import com.springsource.petclinic.domain.QPet;
import com.mysema.query.jpa.JPQLQuery;

@RooJpaRepositoryCustomImpl(repository = PetRepositoryCustom.class)
public class PetRepositoryImpl extends QueryDslRepositorySupport{

    PetRepositoryImpl() {
        super(Pet.class);
    }
    
    private JPQLQuery getQueryFrom(QPet qEntity){
        return from(qEntity);
    }
}
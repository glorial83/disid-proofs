package com.springsource.petclinic.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.springsource.petclinic.domain.Owner;
import com.springsource.petclinic.domain.QOwner;
import com.mysema.query.jpa.JPQLQuery;

@RooJpaRepositoryCustomImpl(repository = OwnerRepositoryCustom.class)
public class OwnerRepositoryImpl extends QueryDslRepositorySupport{

    OwnerRepositoryImpl() {
        super(Owner.class);
    }
    
    private JPQLQuery getQueryFrom(QOwner qEntity){
        return from(qEntity);
    }
}
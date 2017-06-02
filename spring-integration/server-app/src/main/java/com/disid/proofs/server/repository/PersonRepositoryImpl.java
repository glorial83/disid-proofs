package com.disid.proofs.server.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proofs.server.domain.Person;

/**
 * = PersonRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = PersonRepositoryCustom.class)
public class PersonRepositoryImpl extends QueryDslRepositorySupportExt<Person> {

    /**
     * TODO Auto-generated constructor documentation
     */
    PersonRepositoryImpl() {
        super(Person.class);
    }
}
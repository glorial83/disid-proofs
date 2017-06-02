package com.disid.proofs.client.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proofs.client.domain.Person;

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
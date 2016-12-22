package org.springframework.roo.multiselect.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import org.springframework.roo.multiselect.domain.Vet;

/**
 * = VetRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = VetRepositoryCustom.class)
public class VetRepositoryImpl extends QueryDslRepositorySupportExt<Vet> {

    /**
     * TODO Auto-generated constructor documentation
     */
    VetRepositoryImpl() {
        super(Vet.class);
    }
}
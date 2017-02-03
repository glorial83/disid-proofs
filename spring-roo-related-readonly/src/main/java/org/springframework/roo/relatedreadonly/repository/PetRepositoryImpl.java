package org.springframework.roo.relatedreadonly.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import org.springframework.roo.relatedreadonly.domain.Pet;

/**
 * = PetRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = PetRepositoryCustom.class)
public class PetRepositoryImpl extends QueryDslRepositorySupportExt<Pet> {

    /**
     * TODO Auto-generated constructor documentation
     */
    PetRepositoryImpl() {
        super(Pet.class);
    }
}
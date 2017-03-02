package org.disid.proof.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import org.disid.proof.domain.Editorial;

/**
 * = EditorialRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = EditorialRepositoryCustom.class)
public class EditorialRepositoryImpl extends QueryDslRepositorySupportExt<Editorial> {

    /**
     * TODO Auto-generated constructor documentation
     */
    EditorialRepositoryImpl() {
        super(Editorial.class);
    }
}
package org.disid.proof.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import org.disid.proof.domain.Cover;

/**
 * = CoverRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = CoverRepositoryCustom.class)
public class CoverRepositoryImpl extends QueryDslRepositorySupportExt<Cover> {

    /**
     * TODO Auto-generated constructor documentation
     */
    CoverRepositoryImpl() {
        super(Cover.class);
    }
}
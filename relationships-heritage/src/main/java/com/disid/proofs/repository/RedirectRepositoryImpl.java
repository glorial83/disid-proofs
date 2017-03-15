package com.disid.proofs.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proofs.domain.Redirect;

/**
 * = RedirectRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = RedirectRepositoryCustom.class)
public class RedirectRepositoryImpl extends QueryDslRepositorySupportExt<Redirect> {

    /**
     * TODO Auto-generated constructor documentation
     */
    RedirectRepositoryImpl() {
        super(Redirect.class);
    }
}
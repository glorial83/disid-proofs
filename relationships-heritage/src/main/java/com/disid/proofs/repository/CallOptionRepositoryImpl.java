package com.disid.proofs.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proofs.domain.CallOption;

/**
 * = CallOptionRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = CallOptionRepositoryCustom.class)
public class CallOptionRepositoryImpl extends QueryDslRepositorySupportExt<CallOption> {

    /**
     * TODO Auto-generated constructor documentation
     */
    CallOptionRepositoryImpl() {
        super(CallOption.class);
    }
}
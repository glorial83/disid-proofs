package com.disid.proofs.client.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proofs.client.domain.Operation;

/**
 * = OperationRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = OperationRepositoryCustom.class)
public class OperationRepositoryImpl extends QueryDslRepositorySupportExt<Operation> {

    /**
     * TODO Auto-generated constructor documentation
     */
    OperationRepositoryImpl() {
        super(Operation.class);
    }
}
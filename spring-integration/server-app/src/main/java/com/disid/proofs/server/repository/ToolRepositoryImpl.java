package com.disid.proofs.server.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proofs.server.domain.Tool;

/**
 * = ToolRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = ToolRepositoryCustom.class)
public class ToolRepositoryImpl extends QueryDslRepositorySupportExt<Tool> {

    /**
     * TODO Auto-generated constructor documentation
     */
    ToolRepositoryImpl() {
        super(Tool.class);
    }
}
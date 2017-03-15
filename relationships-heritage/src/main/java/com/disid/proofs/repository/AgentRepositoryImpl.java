package com.disid.proofs.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proofs.domain.Agent;

/**
 * = AgentRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = AgentRepositoryCustom.class)
public class AgentRepositoryImpl extends QueryDslRepositorySupportExt<Agent> {

    /**
     * TODO Auto-generated constructor documentation
     */
    AgentRepositoryImpl() {
        super(Agent.class);
    }
}
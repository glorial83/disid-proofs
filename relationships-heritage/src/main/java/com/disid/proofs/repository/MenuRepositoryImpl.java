package com.disid.proofs.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proofs.domain.Menu;

/**
 * = MenuRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = MenuRepositoryCustom.class)
public class MenuRepositoryImpl extends QueryDslRepositorySupportExt<Menu> {

    /**
     * TODO Auto-generated constructor documentation
     */
    MenuRepositoryImpl() {
        super(Menu.class);
    }
}
package org.disid.proof.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import org.disid.proof.domain.Author;

/**
 * = AuthorRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = AuthorRepositoryCustom.class)
public class AuthorRepositoryImpl extends QueryDslRepositorySupportExt<Author> {

    /**
     * TODO Auto-generated constructor documentation
     */
    AuthorRepositoryImpl() {
        super(Author.class);
    }
}
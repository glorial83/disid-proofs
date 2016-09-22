package com.springsource.petclinic.repository;
import com.springsource.petclinic.domain.Owner;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = Owner.class, defaultSearchResult = Owner.class)
public interface OwnerRepositoryCustom {
}

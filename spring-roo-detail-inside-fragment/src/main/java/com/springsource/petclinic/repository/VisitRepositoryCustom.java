package com.springsource.petclinic.repository;
import com.springsource.petclinic.domain.Visit;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = Visit.class, defaultSearchResult = Visit.class)
public interface VisitRepositoryCustom {
}

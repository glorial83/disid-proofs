package com.springsource.petclinic.repository;
import com.springsource.petclinic.domain.Vet;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = Vet.class, defaultSearchResult = Vet.class)
public interface VetRepositoryCustom {
}

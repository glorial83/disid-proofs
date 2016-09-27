package com.springsource.petclinic.repository;
import com.springsource.petclinic.domain.Pet;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = Pet.class, defaultSearchResult = Pet.class)
public interface PetRepositoryCustom {
}

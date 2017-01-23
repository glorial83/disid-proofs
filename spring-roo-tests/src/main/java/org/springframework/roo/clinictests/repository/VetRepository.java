package org.springframework.roo.clinictests.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.clinictests.domain.Vet;
import org.springframework.roo.clinictests.domain.VetInfo;

/**
 * = VetRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Vet.class, defaultReturnType = VetInfo.class)
public interface VetRepository {
}

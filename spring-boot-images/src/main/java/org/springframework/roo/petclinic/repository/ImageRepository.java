package org.springframework.roo.petclinic.repository;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.petclinic.domain.Image;
import org.springframework.transaction.annotation.Transactional;

/**
 * = PetRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@Transactional(readOnly = true)
public interface ImageRepository extends DetachableJpaRepository<Image, Long>{
}

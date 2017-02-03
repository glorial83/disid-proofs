package org.springframework.roo.relatedreadonly.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.relatedreadonly.domain.Owner;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooFinder;
import org.springframework.roo.relatedreadonly.domain.OwnerFirstNameFormBean;

/**
 * = OwnerRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Owner.class, finders = { @RooFinder(value = "findByFirstNameLike", returnType = Owner.class, formBean = OwnerFirstNameFormBean.class) })
public interface OwnerRepository {
}

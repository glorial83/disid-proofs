package org.springframework.roo.entityformat.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.entityformat.domain.Owner;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooFinder;
import org.springframework.roo.entityformat.domain.OwnerFirstNameFormBean;
import org.springframework.roo.entityformat.domain.OwnerCityFormBean;
import org.springframework.roo.entityformat.domain.OwnerInfo;

/**
 * = OwnerRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Owner.class, finders = { @RooFinder(value = "findByFirstNameLike", formBean = OwnerFirstNameFormBean.class), @RooFinder(value = "findByCityLike", returnType = OwnerInfo.class, formBean = OwnerCityFormBean.class) })
public interface OwnerRepository {
}

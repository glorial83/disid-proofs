package org.springframework.roo.clinictests.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.clinictests.domain.Owner;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooFinder;
import org.springframework.roo.clinictests.domain.OwnerFirstNameFormBean;
import org.springframework.roo.clinictests.domain.OwnerCityFormBean;
import org.springframework.roo.clinictests.domain.OwnerInfo;

/**
 * = OwnerRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Owner.class, finders = { @RooFinder(value = "findByFirstNameLike", returnType = Owner.class, formBean = OwnerFirstNameFormBean.class), @RooFinder(value = "findByCityLike", returnType = OwnerInfo.class, formBean = OwnerCityFormBean.class) })
public interface OwnerRepository {
}

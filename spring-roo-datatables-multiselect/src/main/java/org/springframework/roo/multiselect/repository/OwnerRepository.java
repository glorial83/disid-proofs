package org.springframework.roo.multiselect.repository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.roo.multiselect.domain.Owner;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooFinder;
import org.springframework.roo.multiselect.domain.OwnerFirstNameFormBean;
import org.springframework.roo.multiselect.domain.OwnerCityFormBean;
import org.springframework.roo.multiselect.domain.OwnerInfo;

/**
 * = OwnerRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Owner.class, finders = { @RooFinder(value = "findByFirstNameLike", formBean = OwnerFirstNameFormBean.class), @RooFinder(value = "findByCityLike", returnType = OwnerInfo.class, formBean = OwnerCityFormBean.class) })
public interface OwnerRepository {
}

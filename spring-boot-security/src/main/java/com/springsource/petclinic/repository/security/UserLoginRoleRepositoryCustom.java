package com.springsource.petclinic.repository.security;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

import com.springsource.petclinic.domain.security.UserLoginRole;

@RooJpaRepositoryCustom(entity = UserLoginRole.class, defaultSearchResult = UserLoginRole.class)
public interface UserLoginRoleRepositoryCustom {

}

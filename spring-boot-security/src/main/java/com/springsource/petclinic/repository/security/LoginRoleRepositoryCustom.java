package com.springsource.petclinic.repository.security;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

import com.springsource.petclinic.domain.security.LoginRole;

@RooJpaRepositoryCustom(entity = LoginRole.class, defaultSearchResult = LoginRole.class)
public interface LoginRoleRepositoryCustom {
  
}

package com.springsource.petclinic.repository.security;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

import com.springsource.petclinic.domain.security.UserLogin;
import com.springsource.petclinic.domain.security.UserLoginDetails;

@RooJpaRepositoryCustom(entity = UserLogin.class, defaultSearchResult = UserLogin.class)
public interface UserLoginRepositoryCustom {
  
  UserLoginDetails findByUsername(UserLogin login);
  
}

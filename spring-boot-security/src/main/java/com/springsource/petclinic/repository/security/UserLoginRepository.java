package com.springsource.petclinic.repository.security;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

import com.springsource.petclinic.domain.security.UserLogin;

@RooJpaRepository(entity = UserLogin.class)
public interface UserLoginRepository {

}

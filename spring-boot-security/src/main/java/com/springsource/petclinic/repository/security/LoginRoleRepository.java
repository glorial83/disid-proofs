package com.springsource.petclinic.repository.security;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

import com.springsource.petclinic.domain.security.LoginRole;

@RooJpaRepository(entity = LoginRole.class)
public interface LoginRoleRepository {

}

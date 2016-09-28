package com.springsource.petclinic.repository.security;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

import com.springsource.petclinic.domain.security.UserLoginRole;

@RooJpaRepository(entity = UserLoginRole.class)
public interface UserLoginRoleRepository {

}

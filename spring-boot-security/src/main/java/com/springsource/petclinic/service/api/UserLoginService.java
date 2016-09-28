package com.springsource.petclinic.service.api;

import org.springframework.roo.addon.layers.service.annotations.RooService;

import com.springsource.petclinic.domain.security.UserLogin;
import com.springsource.petclinic.domain.security.UserLoginDetails;

@RooService(entity = UserLogin.class)
public interface UserLoginService {
  
  UserLoginDetails findByUsername(UserLogin user);

}

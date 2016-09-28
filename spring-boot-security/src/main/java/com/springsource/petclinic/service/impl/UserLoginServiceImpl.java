package com.springsource.petclinic.service.impl;

import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;

import com.springsource.petclinic.domain.security.UserLogin;
import com.springsource.petclinic.domain.security.UserLoginDetails;
import com.springsource.petclinic.service.api.UserLoginService;

@RooServiceImpl(service = UserLoginService.class)
public class UserLoginServiceImpl {

  @Override
  public UserLoginDetails findByUsername(UserLogin user){
    return userLoginRepository.findByUsername(user);
  }
  
}

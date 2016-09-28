package com.springsource.petclinic.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsource.petclinic.domain.security.UserLogin;
import com.springsource.petclinic.domain.security.UserLoginDetails;
import com.springsource.petclinic.service.api.UserLoginService;

@Service
public class PetclinicUserDetailsService implements UserDetailsService {

  private final UserLoginService userLoginService;
  
  @Autowired
  public PetclinicUserDetailsService(UserLoginService userLoginService){
    this.userLoginService = userLoginService;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserLogin userLogin = new UserLogin();
    userLogin.setUsername(username);

    UserLoginDetails details = userLoginService.findByUsername(userLogin);
    if (details == null) {
      throw new UsernameNotFoundException(username);
    }
    return new PetclinicUserDetails(details);
  }

  
}
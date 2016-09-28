package com.springsource.petclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springsource.petclinic.web.security.PetclinicUserDetailsService;

@Configuration
public class GlobalSecurityConfig extends GlobalAuthenticationConfigurerAdapter {
  
  @Autowired
  private PetclinicUserDetailsService userDetailsService;

  @Autowired
  private AuthenticationEventPublisher authenticationEventPublisher;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
      
      // Allow to listen authentication events
      auth.authenticationEventPublisher(authenticationEventPublisher);

      // Set the userDetailsService to use and the mechanism to encode the password
      auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }


}

package com.springsource.petclinic.domain.security;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.annotations.RooEquals;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

@RooJpaEntity
@RooJavaBean
@RooEquals
@RooToString
public class UserLoginRole {

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private UserLogin userLogin;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private LoginRole loginRole;


}

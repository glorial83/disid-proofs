package com.springsource.petclinic.domain.security;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.annotations.RooEquals;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

@RooJpaEntity
@RooJavaBean
@RooEquals
@RooToString
public class LoginRole {

  @NotNull
  @Size(max = 20)
  private String name;

  @NotNull
  @Size(max = 50)
  private String description;

  
}

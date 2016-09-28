package com.springsource.petclinic.domain.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.annotations.RooEquals;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

@RooJpaEntity
@RooJavaBean
@RooEquals
@RooToString
public class UserLogin {

  @NotNull
  @Size(max = 30)
  @Pattern(regexp = "^[^<>\\\\'\"&;%]*$")
  private String username;

  @NotNull
  @Size(max = 255)
  private String password;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userLogin")
  private Set<UserLoginRole> userLoginRoles = new HashSet<UserLoginRole>();

}

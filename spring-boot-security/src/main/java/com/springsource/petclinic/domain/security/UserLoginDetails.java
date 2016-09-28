package com.springsource.petclinic.domain.security;

import java.util.Calendar;
import java.util.Set;

public class UserLoginDetails {

  private final Long id;
  private final String username;
  private final String password;
  private final Set<String> userLoginRoles;

  public UserLoginDetails(Long id, String username, String password,
      Set<String> userLoginRoles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.userLoginRoles = userLoginRoles;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Set<String> getUserLoginRoles() {
    return userLoginRoles;
  }

  private Calendar now() {
    return Calendar.getInstance();
  }

}

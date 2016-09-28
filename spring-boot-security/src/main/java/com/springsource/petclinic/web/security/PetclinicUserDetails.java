package com.springsource.petclinic.web.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.roo.addon.javabean.annotations.RooEquals;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springsource.petclinic.domain.security.UserLoginDetails;

@RooEquals
public class PetclinicUserDetails implements UserDetails, CredentialsContainer{

  private Long id;
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> roles;
  
  PetclinicUserDetails(UserLoginDetails user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.password = user.getPassword();

    if (user.getUserLoginRoles() == null) {
      this.roles = Collections.emptySet();
    } else {
      Set<SimpleGrantedAuthority> roleSet =
          new HashSet<SimpleGrantedAuthority>(user.getUserLoginRoles().size());
      for (String role : user.getUserLoginRoles()) {
        roleSet.add(new SimpleGrantedAuthority(role));
      }
      this.roles = roleSet;
    }

  }

  @Override
  public void eraseCredentials() {
    this.password = null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
  
}

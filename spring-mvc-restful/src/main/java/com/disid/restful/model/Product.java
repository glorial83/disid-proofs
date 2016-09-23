package com.disid.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@RooJavaBean
@RooToString
@RooJpaEntity
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
public class Product {

  /**
   */
  private String name;

  /**
   */
  private String description;

  /**
   * Bidirectional aggregation many-to-many relationship. Child side.
   */
  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Category> categories = new HashSet<Category>();
}

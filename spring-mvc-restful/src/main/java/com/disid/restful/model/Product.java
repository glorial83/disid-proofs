package com.disid.restful.model;

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
  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Category> categories = new HashSet<Category>();
}

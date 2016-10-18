package com.disid.restful.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooJpaEntity
@EntityListeners(AuditingEntityListener.class)
@Table(name="my_category")
public class Category {

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private Date createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private Date lastModifiedDate;

  @Size(min = 3, max = 30)
  @Column(name="my_name")
  private String name;

  @Column(name="my_description")
  private String description;

  /**
   * Bidirectional aggregation many-to-many relationship. Parent side.
   */
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "categories",
      fetch = FetchType.LAZY)
  private Set<Product> products = new HashSet<Product>();

  /**
   * Adds a list of products to the category, taking care to update the
   * relationship from the {@link Product} to the
   * {@link Category} either.
   * @param productsToAdd products to add to the category (required)
   * @throws IllegalArgumentException if products is null or empty
   */
  public void addToProducts(Iterable<Product> productsToAdd) {
    Assert.notNull(productsToAdd, "The given Iterable of products to add can't be null!");
    for (Product product : productsToAdd) {
      this.products.add(product);
      product.getCategories().add(this);
    }
  }

  /**
   * Removes a list of products from this category, taking care to update the
   * relationship from the {@link Product} to the
   * {@link Category} either.
   * @param productsToRemove products to remove from the customer (required)
   * @throws IllegalArgumentException if products is empty
   */
  public void removeFromProducts(Iterable<Product> productsToRemove) {
    Assert.notNull(productsToRemove, "The given Iterable of products to remove can't be null!");
    for (Product product : productsToRemove) {
      this.products.remove(product);
      product.getCategories().remove(this);
    }
  }
}

package com.disid.restful.model;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooJpaEntity
public class Category {

  @Size(min = 3, max = 30)
  private String name;

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
  public void addToProducts(Collection<Product> productsToAdd) {
    Assert.notEmpty(productsToAdd, "At least one product to add is required");
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
  public void removeFromProducts(Collection<Product> productsToRemove) {
    Assert.notEmpty(productsToRemove, "At least one product to remove is required");
    for (Product product : productsToRemove) {
      this.products.remove(product);
      product.getCategories().remove(this);
    }
  }
}

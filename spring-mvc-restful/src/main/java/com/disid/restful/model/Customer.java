package com.disid.restful.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@RooJavaBean
@RooToString
@RooJpaEntity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer {

  /**
   */
  private String firstName;

  /**
   */
  private String lastName;

  /**
   * Bidirectional aggregation one-to-many relationship. Parent side.
   */
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "customer",
      fetch = FetchType.LAZY)
  private Set<CustomerOrder> orders = new HashSet<CustomerOrder>();

  /**
   * Bidirectional composition one-to-one relationship. Parent side.
   */
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer",
      fetch = FetchType.LAZY)
  private Address address;

  /**
   * Sets the address for the customer, taking
   * care to update the relationship from the {@link Address} to the
   * {@link Customer} either.
   * If address is null the {@link #removeAddress()} method is called 
   * instead.
   * @param address for the customer
   */
  public void setAddress(Address address) {
    if (address == null) {
      removeAddress();
    } else {
      this.address = address;
      address.setCustomer(this);
    }
  }

  /**
   * Removes the relationship between the customer and its address, taking
   * care to update the relationship from the {@link Address} to the
   * {@link Customer} either.
   */
  public void removeAddress() {
    if (this.address != null) {
      address.setCustomer(null);
    }
    this.address = null;
  }

  /**
   * Adds a list of orders for the customer, taking care to update the 
   * relationship from the {@link CustomerOrder} to the
   * {@link Customer} either.
   * @param ordersToAdd to add to the customer (required)
   * @throws IllegalArgumentException if ordersToAdd is null or emptu
   */
  public void addToOrders(Collection<CustomerOrder> ordersToAdd) {
    Assert.notEmpty(ordersToAdd, "At least one order to add is required");
    for (CustomerOrder order : ordersToAdd) {
      this.orders.add(order);
      order.setCustomer(this);
    }
  }

  /**
   * Removes a list of ordesr from the customer, taking care to update the 
   * relationship from the {@link CustomerOrder} to the
   * {@link Customer} either.
   * @param ordersToRemove to remove from the customer (required)
   * @throws IllegalArgumentException if order is null or it isn't
   * an order of this customer.
   */
  public void removeFromOrders(Collection<CustomerOrder> ordersToRemove) {
    Assert.notEmpty(ordersToRemove, "At least one order to remove is required");
    for (CustomerOrder order : ordersToRemove) {
      this.orders.remove(order);
      order.setCustomer(null);
    }
  }

  public String toString() {
    String text = super.toString() + "- First name: " + firstName + ", Last name: " + lastName;
    if (address != null) {
      text = text + ", Address: {" + address.toString() + "}";
    }
    return text;
  }
}

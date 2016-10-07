package com.disid.restful.model;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@RooJavaBean
@RooToString
@RooJpaEntity
@Table(name="my_address")
public class Address {

  /**
   */
  @Column(name= "my_street")
  private String street;

  /**
   */
  @Column(name= "my_city")
  private String city;

  /**
   */
  @Min(1L)
  @Column(name= "my_streetnumber")
  private Integer streetNumber;

  /**
   * Bidirectional composition one-to-one relationship. Child side.
   */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name= "my_customer")
  private Customer customer;

  public String toString() {
    return super.toString() + " - Street: " + street + ", City: " + city + ", Number: "
        + streetNumber;
  }
}

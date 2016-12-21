package com.disid.restful.model;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaEntity
@Entity
public class OrderDetail {

  /**
   * Number of products in the order detail.
   */
  private Integer quantity;

  /**
   * Unidirectional many-to-one relationship.
   */
  @ManyToOne
  @NotNull
  private Product product;

  /**
   * Bidirectional composition many-to-one relationship. Child side.
   */
  @ManyToOne
  @MapsId("customerOrderId")
  @JoinColumn(name = "customerOrderId", referencedColumnName = "id")
  private CustomerOrder customerOrder;

  @EmbeddedId
  private OrderDetailPK id;

  @Version
  @Column(name = "version")
  private Integer version;

  public OrderDetailPK getId() {
    return this.id;
  }

  public void setId(OrderDetailPK id) {
    this.id = id;
  }

  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}

package com.disid.restful.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@RooJavaBean
@RooToString
@RooJpaEntity
@Table(name = "my_customerorder")
public class CustomerOrder {

  /**
   */
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Column(name = "my_orderdate")
  private Date orderDate;

  /**
   */
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Column(name = "my_shippeddate")
  private Date shippedDate;

  /**
   */
  @Column(name = "my_shipaddress")
  private String shipAddress;

  /**
  * Bidirectional composition one-to-many relationship. Parent side.
  * TODO: convertir a List la propiedad details, tal y como está ahora el
  * remove de lineas y luego añadir no funcionará bien.
  */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY,
      mappedBy = "customerOrder")
  private Set<OrderDetail> details = new HashSet<OrderDetail>();

  /**
   * Bidirectional aggregation many-to-one relationship. Child side.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "my_customer_orders",
      joinColumns = @JoinColumn(name = "my_order", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "my_customer", referencedColumnName = "id"))
  private Customer customer;

  /**
   * Adds a list of <b>new</b> details to the order, taking care to update the
   * relationship from the {@link OrderDetail} to the
   * {@link CustomerOrder} either.
   * @param details to add to the order (required)
   * @throws IllegalArgumentException if the detail is null or empty
   */
  public void addToDetails(Iterable<OrderDetail> detailsToAdd) {
    Assert.notNull(detailsToAdd, "The given Iterable of order details to add can't be null!");
    int lastDetail = getDetails().size();
    for (OrderDetail detail : detailsToAdd) {
      OrderDetailPK detailPK = new OrderDetailPK(getId(), lastDetail);
      details.add(detail);
      detail.setId(detailPK);
      detail.setCustomerOrder(this);
      lastDetail++;
    }
  }

  /**
   * Removes a detail from the order, taking care to update the
   * relationship from the {@link OrderDetail} to the
   * {@link CustomerOrder} either.
   * As the {@link #details} property is configured with orphanRemoval
   * to true, the detail will be removed when this order is persisted.
   * @param detail to remove from the order (required)
   * @throws IllegalArgumentException if the detail is null or it isn't
   * a detail of this customer.
   */
  public void removeFromDetails(Iterable<OrderDetail> detailsToRemove) {
    Assert.notNull(detailsToRemove, "The given Iterable of order details to remove can't be null!");
    for (OrderDetail detail : detailsToRemove) {
      details.remove(detail);
      detail.setCustomerOrder(null);
    }
  }

}

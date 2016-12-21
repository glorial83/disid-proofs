package com.disid.restful.model;

import org.springframework.roo.addon.javabean.annotations.RooEquals;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@RooJavaBean
@RooToString
@Embeddable
@RooEquals
public class OrderDetailPK implements Serializable {

  private static final long serialVersionUID = 8834571479446197507L;

  private Integer id;

  @Column(name = "customerOrderId")
  private Long customerOrderId;

  public OrderDetailPK() {
    // Nothing to do
  }

  public OrderDetailPK(Long customerOrderId, Integer pos) {
    super();
    this.id = pos;
    this.customerOrderId = customerOrderId;
  }


	public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
        this.id = id;
    }

	public Long getCustomerOrderId() {
        return this.customerOrderId;
    }

	public void setCustomerOrderId(Long customerOrderId) {
        this.customerOrderId = customerOrderId;
    }
}

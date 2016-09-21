package com.disid.restful.model;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@RooJavaBean
@RooToString
@RooJpaEntity(identifierType = OrderDetailPK.class)
public class OrderDetail {

    /**
     * Number of products in the order detail.
     */
    private Integer quantity;

    /**
     * Unidirectional many-to-one relationship.
     */
    @ManyToOne
    private Product product;

    /**
     * Bidirectional aggregation many-to-one relationship. Child side.
     */
    @ManyToOne
    @MapsId("customerOrderId")
    @JoinColumn(name = "customerOrderId", referencedColumnName = "id")
    private CustomerOrder customerOrder;
}

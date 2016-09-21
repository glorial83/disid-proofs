package com.disid.restful.model;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaEntity
public class Address {

    /**
     */
    private String street;

    /**
     */
    private String city;

    /**
     */
    @Min(1L)
    private Integer streetNumber;

    /**
     * Bidirectional aggregation one-to-one relationship. Child side.
     */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;
}

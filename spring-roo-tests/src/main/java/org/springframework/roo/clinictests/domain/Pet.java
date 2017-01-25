package org.springframework.roo.clinictests.domain;

import io.springlets.format.EntityFormat;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.audit.RooJpaAudit;
import org.springframework.roo.addon.jpa.annotations.entity.JpaRelationType;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaRelation;
import org.springframework.roo.addon.ws.annotations.jaxb.RooJaxbEntity;
import org.springframework.roo.clinictests.domain.reference.PetType;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * = Pet
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(entityFormatExpression = "#{name} (#{type})")
@RooJpaAudit
@RooJaxbEntity
public class Pet {

  /**
   * TODO Auto-generated field documentation
   *
   */
  @Id
  @SequenceGenerator(name = "petGen", sequenceName = "PET_SEQ")
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "petGen")
  private Long id;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @Version
  private Integer version;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @NotNull
  private boolean sendReminders;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @NotNull
  @Size(min = 1)
  private String name;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @NotNull
  @Min(0L)
  @NumberFormat
  private Float weight;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @NotNull
  @Enumerated
  private PetType type;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @OneToMany(cascade = {javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST},
      fetch = FetchType.LAZY, mappedBy = "pet")
  @RooJpaRelation(type = JpaRelationType.AGGREGATION)
  private Set<Visit> visits = new HashSet<Visit>();

  /**
   * TODO Auto-generated field documentation
   *
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @EntityFormat
  private Owner owner;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar createdDate;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @CreatedBy
  private String createdBy;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar modifiedDate;

  /**
   * TODO Auto-generated field documentation
   *
   */
  @LastModifiedBy
  private String modifiedBy;

  /**
   * Empty constructor just needed by the JPA provider.
   */
  public Pet() {}

  /**
   * @param sendReminders
   * @param name
   * @param weight
   * @param type
   */
  public Pet(String name, Float weight, PetType type, boolean sendReminders) {
    Assert.notNull(name, "Pet name must not be null");
    Assert.hasText(name, "Pet name must not be empty");
    Assert.notNull(weight, "Pet weight must not be null");
    Assert.isTrue(weight >= 0f, "Pet weight must be greater than 0");
    Assert.notNull(type, "Pet type must not be null");
    this.name = name;
    this.weight = weight;
    this.type = type;
    this.sendReminders = sendReminders;
  };

}

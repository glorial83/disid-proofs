package org.springframework.roo.entityformat.domain;

import io.springlets.format.EntityFormat;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.JpaRelationType;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaRelation;
import org.springframework.roo.addon.ws.annotations.jaxb.RooJaxbEntity;
import org.springframework.roo.entityformat.reference.PetType;

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
@RooJpaEntity
@RooJaxbEntity
@EntityFormat("#{name} (#{type})")
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
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "pet")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<Visit> visits = new HashSet<Visit>();

    /**
     * TODO Auto-generated field documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @EntityFormat
    private Owner owner;
}

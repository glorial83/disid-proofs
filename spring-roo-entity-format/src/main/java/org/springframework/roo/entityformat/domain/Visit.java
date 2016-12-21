package org.springframework.roo.entityformat.domain;
import io.springlets.format.EntityFormat;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.roo.addon.ws.annotations.jaxb.RooJaxbEntity;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * = Visit
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity
@RooJaxbEntity
@EntityFormat(message = "format_visit")
public class Visit {

    /**
     * TODO Auto-generated field documentation
     *
     */
    @Id
    @SequenceGenerator(name = "visitGen", sequenceName = "VISIT_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "visitGen")
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
    @Size(max = 255)
    private String description;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date visitDate;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @EntityFormat
    private Pet pet;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @EntityFormat
    private Vet vet;
}

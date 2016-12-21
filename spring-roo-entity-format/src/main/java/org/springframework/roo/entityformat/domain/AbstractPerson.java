package org.springframework.roo.entityformat.domain;
import io.springlets.format.EntityFormat;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.roo.addon.ws.annotations.jaxb.RooJaxbEntity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * = AbstractPerson
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity
@RooJaxbEntity
@EntityFormat("#{firstName} #{lastName}")
public abstract class AbstractPerson {

    /**
     * TODO Auto-generated field documentation
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Size(min = 3, max = 30)
    private String firstName;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @NotNull
    @Size(min = 3, max = 30)
    private String lastName;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @NotNull
    @Size(min = 1, max = 50)
    private String address;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @NotNull
    @Size(max = 30)
    private String city;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @NotNull
    private String telephone;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @Size(max = 30)
    private String homePage;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @Size(min = 6, max = 30)
    private String email;

    /**
     * TODO Auto-generated field documentation
     *
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date birthDay;
}

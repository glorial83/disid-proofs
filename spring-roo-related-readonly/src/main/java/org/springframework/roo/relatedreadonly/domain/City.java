package org.springframework.roo.relatedreadonly.domain;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import org.springframework.format.annotation.NumberFormat;

/**
 * = City
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean(settersByDefault = false)
@RooToString
@RooJpaEntity(entityFormatExpression = "#{name}", readOnly = true)
public class City {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Version
    private Integer version;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private String name;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Digits(integer = 5, fraction = 0)
    @NumberFormat
    private int postalCode;
}

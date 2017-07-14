package org.springframework.roo.petclinic.domain;

import io.springlets.data.jpa.domain.EmbeddableImage;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.JpaRelationType;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaRelation;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaEntity
public class Image {

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
   * Image field
   */
  @Embedded
  @NotNull
  private EmbeddableImage image;

  /**
   * TODO Auto-generated attribute documentation
   *
   */
  @OneToMany(cascade = {javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST},
      fetch = FetchType.LAZY, mappedBy = "image")
  @RooJpaRelation(type = JpaRelationType.AGGREGATION)
  private Set<Pet> pets = new HashSet<Pet>();

  /**
   * Default constructor
   */
  public Image() {
    // Nothing to do here
  }

  /**
   * Constructor that receives the embeddable image
   * 
   * @param image
   */
  public Image(EmbeddableImage image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return this.image.toString();
  }

}

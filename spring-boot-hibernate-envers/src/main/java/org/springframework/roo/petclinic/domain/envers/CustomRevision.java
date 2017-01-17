package org.springframework.roo.petclinic.domain.envers;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 * = Entity that extends the revisionLog feature provided
 * by the Hibernate Envers library.
 *
 * This class allows to include our own attributes to the revisionLog.
 * In this case, we're going to save the name of the user that applies 
 * the changes.
 * 
 * Just extend this class if you want to include your own attributes to
 * the revisionLog.
 * 
 */
@Entity
@RevisionEntity(CustomRevisionListener.class)
@Table(name = "CUSTOM_REVISIONS")
public class CustomRevision {

  /** Identifier */
  @Column(name = "CUSTOM_REVISION_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customRevisionGen")
  @Id
  @RevisionNumber
  @SequenceGenerator(name = "customRevisionGen", sequenceName = "CUS_REV_ID_SEQ")
  private Long id;

  /** Date that indicates when the change had been applied */
  @Column(name = "TIMESTAMP")
  @RevisionTimestamp
  private Date timestamp;

  /** Username that had applied the changes */
  @Column(name = "USERNAME")
  private String username;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
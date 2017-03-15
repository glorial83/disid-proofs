package com.disid.proofs.domain;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

/**
 * = AdvancedOption
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity
public abstract class AdvancedOption extends CallOption {
}

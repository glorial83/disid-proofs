package com.disid.proofs.domain;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

import io.springlets.format.EntityFormat;

/**
 * = Redirect
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity
@EntityFormat("Redirect: #{name} - #{phone}")
public class Redirect extends AdvancedOption {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private String phone;
}

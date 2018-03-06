package org.springframework.roo.petclinic.config;

import io.tracee.binding.springmvc.TraceeInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooWebMvcConfiguration;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooWebMvcThymeleafUIConfiguration;
import org.springframework.roo.petclinic.web.DatabaseConfigurationInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * = WebMvcConfiguration
 *
 * TODO Auto-generated class documentation
 *
 */
@RooWebMvcConfiguration( defaultLanguage = "en" )
@RooWebMvcThymeleafUIConfiguration
public class WebMvcConfiguration
{

  @Autowired
  private DataSourceConfigurationValues dataSourceConfigurationValues;

  /**
   * Register interceptors
   *
   * @param registry
   */
  @Override
  public void addInterceptors( InterceptorRegistry registry )
  {
    registry.addInterceptor( localeChangeInterceptor() );
    registry.addInterceptor( new TraceeInterceptor() );

    // Register intercetor which asures datasource configuration is the only accesible page
    // while configuration is not completed
    registry.addInterceptor( new DatabaseConfigurationInterceptor(dataSourceConfigurationValues) );

  }
}

package org.springframework.roo.petclinic.web;

import org.springframework.roo.petclinic.config.DataSourceConfigurationValues;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor which redired all request to DataSource configuration view while
 * configuration is not completed
 */
public class DatabaseConfigurationInterceptor extends HandlerInterceptorAdapter
{

  private final DataSourceConfigurationValues dataSourceConfigurationValues;

  public DatabaseConfigurationInterceptor( DataSourceConfigurationValues dataSourceConfigurationValues )
  {
    this.dataSourceConfigurationValues = dataSourceConfigurationValues;
  }

  @Override
  public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception
  {
    // Don't handle errors
    if (request.getServletPath().startsWith( "/error" )) {
      return super.preHandle( request, response, handler );
    }
    // Check Datasource configuration
    if ( !dataSourceConfigurationValues.isConfigured() && !request.getServletPath().endsWith( "/datasource" ) )
    {
      // Redired to confugration page
      response.addHeader( "configuring-datasource", "true" );
      response.sendRedirect( "/datasource" );
      return false;
    }
    // All Ok: continue
    return super.preHandle( request, response, handler );
  }
}

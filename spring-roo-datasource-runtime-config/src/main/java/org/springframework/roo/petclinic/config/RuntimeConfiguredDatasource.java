package org.springframework.roo.petclinic.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.DataSource;

/**
 * {@link DataSource} implementation which returns current datasource depending on {@link DataSourceConfigurationValues}
 * information.
 */
public class RuntimeConfiguredDatasource extends AbstractDataSource
{

  /**
   * Configuration values Bean
   */
  private final DataSourceConfigurationValues configurationValues;

  /**
   * DataSource for inilitalization propourse
   */
  private DataSource initializationDataSource;

  /**
   * DataSource to use in normal application work
   */
  private DataSource workDataSource;

  /**
   * Version of information form ConfigurationValues which workDataSource was created
   */
  private Integer lastConfigurationVersion = null;

  /**
   * Default constructor
   *
   * @param configurationValues
   */
  public RuntimeConfiguredDatasource( DataSourceConfigurationValues configurationValues )
  {
    this.configurationValues = configurationValues;
  }

  @Override
  public Connection getConnection() throws SQLException
  {
    return resolveDataSource().getConnection();
  }

  /**
   * Create and prepare datasorces to use.
   *
   * If work-connection is not set returns
   *
   * @return
   */
  protected DataSource resolveDataSource()
  {
    if ( initializationDataSource == null )
    {
      // Create the "initialization" datasource. This database is used to start application
      DataSourceBuilder dataSourceBuilder = new DataSourceBuilder( this.getClass().getClassLoader() );
      dataSourceBuilder.driverClassName( configurationValues.getDriverClassName() )
          .url( configurationValues.getInitializationUrl() ).username( configurationValues.getUsername() )
          .password( configurationValues.getPassword() );
      initializationDataSource = dataSourceBuilder.build();
    }
    if ( !configurationValues.isConfigured() )
    {
      // No configured yet: return initialization datasource
      return initializationDataSource;
    }
    // Check if datasource values has been changed
    if ( workDataSource != null && !Objects.equals( configurationValues.getVersion(), lastConfigurationVersion ) )
    {
      // Datasource data has been changed so clean it
      workDataSource = null;
    }
    if ( workDataSource == null )
    {
      // Create working datasource
      DataSourceBuilder dataSourceBuilder = new DataSourceBuilder( this.getClass().getClassLoader() );
      dataSourceBuilder.driverClassName( configurationValues.getDriverClassName() ).url( configurationValues.getUrl() )
          .username( configurationValues.getUsername() ).password( configurationValues.getPassword() );

      workDataSource = dataSourceBuilder.build();
      lastConfigurationVersion = configurationValues.getVersion();

    }
    return workDataSource;
  }

  @Override
  public Connection getConnection( String username, String password ) throws SQLException
  {
    return resolveDataSource().getConnection( username, password );

  }

}

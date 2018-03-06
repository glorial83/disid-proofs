package org.springframework.roo.petclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Configuration class which initializes Customized DataSource
 */
@Configuration
public class DataSourceConfiguration
{

  @Bean
  public DataSource getDataSource(DataSourceConfigurationValues values) {
    return new RuntimeConfiguredDatasource( values );
  }

}

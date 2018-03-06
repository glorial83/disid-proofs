package org.springframework.roo.petclinic.config;

import com.google.common.base.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Bean than stores and manages DataSource application configuration
 *
 * Use {@link DataSourceProperties} to get common configuration (as JDBC driver) and generate the connection URL based
 * on DB file set.
 *
 */
@Component
public class DataSourceConfigurationValues
{

  @Autowired
  private DataSourceProperties properties;

  private int version = 0;
  private File file = null;

  /**
   * @return informs if Work DataSource configuration is completed
   */
  public boolean isConfigured()
  {
    return file != null;
  }

  /**
   * @return target DB file storage
   */
  public File getFile()
  {
    return file;
  }

  /**
   * Sets taget DB file storage
   *
   * @param file
   */
  public void setFile( File file )
  {
    if ( !Objects.equal( this.file, file ) )
    {
      this.file = file;
      // Increments version so anyone can check if its has been changes
      version++;
    }

  }

  /**
   * @return informs version of current status
   */
  public Integer getVersion()
  {
    return version;
  }

  /**
   * @return JDBC driver class name
   */
  public String getDriverClassName()
  {
    return properties.getDriverClassName();
  }

  /**
   * @return URL for work datasource connection
   */
  public String getUrl()
  {
    return String.format( "jdbc:hsqldb:file:%s;", this.file.getAbsolutePath() );
  }

  /**
   * @return URL for initialization datasource connection
   */
  public String getInitializationUrl()
  {
    return properties.getUrl();
  }

  public String getUsername()
  {
    return properties.getUsername();
  }

  public String getPassword()
  {
    return properties.getPassword();
  }

}
